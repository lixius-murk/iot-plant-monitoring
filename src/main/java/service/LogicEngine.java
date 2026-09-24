package service;

import model.entity.Command;
import model.entity.Event;
import model.entity.PlantInstance;
import model.entity.Recommendation;
import model.entity.RecommendationMsg;
import model.entity.Telemetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import repo.RecommendationMsgRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LogicEngine {

    @Autowired
    private EventService eventService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private MockDeviceGateway deviceGateway;

    @Autowired
    private PlantService plantService;

    @Autowired
    private TelemetryService telemetryService;

    //events and recs
    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private RecommendationMsgRepository recommendationMsgRepository;

    public void check(Telemetry telemetry, PlantInstance plant) {
        List<Event> triggeredEvents = new ArrayList<>();
        boolean hasProblem = false;

        if (telemetry.getSoilMoisture() != null) {
            int minMoisture = getEffectiveSoilMoistureMin(plant);

            if (telemetry.getSoilMoisture() < minMoisture) {
                hasProblem = true;
                addRecommendation(plant, 3L, "CRITICAL");
                triggeredEvents.add(createEvent(plant, "WATERING", "Автоматический полив для "+plant.getName()));
            }
        }

        if (telemetry.getTemp() != null) {
            BigDecimal minTemp = getEffectiveTempMin(plant);

            if (telemetry.getTemp().compareTo(minTemp) < 0) {
                hasProblem = true;
                addRecommendation(plant, 1L, "CRITICAL");
                triggeredEvents.add(createEvent(plant, "HEATING", "Включен обогрев для "+plant.getName()));
            }
        }

        if (telemetry.getLight() != null) {
            int minLight = getEffectiveLightMin(plant);

            if (telemetry.getLight() < minLight) {
                hasProblem = true;
                addRecommendation(plant, 4L, "CRITICAL");
                triggeredEvents.add(createEvent(plant, "LIGHT_CONTROL", "Включено освещение для "+plant.getName()));
            }
        }
        if (telemetry.getHumidity() != null) {
            Integer minHumidity = getEffectiveHumMin(plant);

            if (minHumidity != null && telemetry.getHumidity() < minHumidity) {
                hasProblem = true;
                addRecommendation(plant, 2L, "CRITICAL");
                triggeredEvents.add(createEvent(plant, "HUMIDIFYING", "Включен увлажнитель для "+plant.getName()));
            }
        }

        List<Event> savedEvents = eventService.saveAll(triggeredEvents);
        for (Event event : savedEvents) {
            webSocketService.sendEvent(event);

            dispatchCommandFor(plant, event);
        }

        Integer newState = hasProblem ? 1 : 0;
        plant.setState(newState);
        plantService.updateState(plant.getId(), newState);
    }

    private void addRecommendation(PlantInstance plant, Long msgId, String severity) {
        if (recommendationService.existsUnresolved(plant.getId(), msgId)) return;

        RecommendationMsg msg = recommendationMsgRepository.findById(msgId).orElse(null);
        if (msg == null) return;

        Recommendation rec = new Recommendation(plant, msg, severity);
        recommendationService.save(rec);
        webSocketService.sendRecommendation(plant, rec);

    }

    private void dispatchCommandFor(PlantInstance plant, Event event) {
        Command command;
        switch (event.getType()) {
            case "WATERING":
                command = commandService.createCommand(plant, event, "WATERING");
                break;
            case "HEATING":
                command = commandService.createCommand(plant, event, "HEATING");
                break;
            case "HUMIDIFYING":
                command = commandService.createCommand(plant, event, "HUMIDIFYING");
                break;
            case "LIGHT_CONTROL":
                command = commandService.createCommand(plant, event, "CURTAINS_OPEN");
                break;
            default:
                return;
        }
        commandService.sendCommand(command);
        deviceGateway.sendCommand(command);
        eventService.markCommandSent(event.getId());
    }

    private Event createEvent(PlantInstance plant, String type, String action) {
        Event event = new Event();
        event.setPlantId(plant.getId());
        event.setType(type);
        event.setAction(action);
        event.setTime(LocalDateTime.now());
        event.setStatus(0); // PENDING
        return event;
    }

    private Integer getEffectiveSoilMoistureMin(PlantInstance plant) {
        return plant.getSoilMoistureMin() != null
                ? plant.getSoilMoistureMin()
                : plant.getSpecies().getSoilMoistureMin();
    }

    private BigDecimal getEffectiveTempMin(PlantInstance plant) {
        return plant.getTempMin() != null
                ? plant.getTempMin()
                : plant.getSpecies().getTempMin();
    }

    private Integer getEffectiveLightMin(PlantInstance plant) {
        return plant.getLightMin() != null
                ? plant.getLightMin()
                : plant.getSpecies().getLightMin();
    }
    private Integer getEffectiveHumMin(PlantInstance plant) {
        return plant.getAirHumMin() != null
                ? plant.getAirHumMin()
                : plant.getSpecies().getAirHumMin();
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void checkGrowth() {
        RecommendationMsg repotMsg = recommendationMsgRepository.findById(9L).orElse(null);
        if (repotMsg == null) return;
        List<PlantInstance> plants = plantService.getAllActive();
        for (PlantInstance plant : plants) {
            Telemetry latestTelemetry = telemetryService.getLatestByPlant(plant.getId()).orElse(null);
            if (latestTelemetry != null && latestTelemetry.getSoilMoisture() != null) {
                if (plant.getHeight() != null && plant.getPotSize() != null){
                    BigDecimal currentHeight = plant.getHeight();
                    BigDecimal potSize = BigDecimal.valueOf(plant.getPotSize());
                    BigDecimal recommendedSize = plant.getSpecies().getRecommendedPotSize() != null
                            ? BigDecimal.valueOf(plant.getSpecies().getRecommendedPotSize()) : BigDecimal.valueOf(40);
                    if (currentHeight.compareTo(potSize.multiply(BigDecimal.valueOf(0.9))) > 0) {
                        Recommendation rec = new Recommendation();
                        rec.setPlant(plant);
                        rec.setMessage(repotMsg);
                        rec.setSeverity("INFO");
                        rec.setCreatedAt(LocalDateTime.now());
                        rec.setResolved(false);
                        recommendationService.save(rec);
                    }


                };

            };
        }
    }
}