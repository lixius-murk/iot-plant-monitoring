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
import simulator.DataSimulator;

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
    private DataSimulator dataSimulator;
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

    @Autowired
    private WebSocketService webSocketService;


    @Autowired
    private RecommendationMsgRepository recommendationMsgRepository;

//
//    public void check(Telemetry telemetry, PlantInstance plant, WebSocketService webSocketService) {
//        List<Event> triggeredEvents = new ArrayList<>();
//        int minMoisture = getEffectiveSoilMoistureMin(plant);
//        plant.setState(0);
//
//        if (telemetry.getSoilMoisture() != null) {
//
//            if (telemetry.getSoilMoisture() < minMoisture
//                    && !eventService.hasOpenEvent(plant.getId(), "WATERING")) {
//                triggeredEvents.add(createEvent(plant, "WATERING",
//                        "Низкая влажность почвы: " + plant.getName()));
//                recommendationService.createIfAbsent(plant, 3L, "WARNING");
//
//                plant.setState(1);
//                System.out.println("trigger for hum");
//
//            } else if (telemetry.getSoilMoisture() >= minMoisture) {
//                eventService.resolveOpen(plant.getId(), "WATERING");
//            }
//        }
//
//        if (telemetry.getTemp() != null) {
//            BigDecimal minTemp = getEffectiveTempMin(plant);
//
//            if (telemetry.getTemp().compareTo(minTemp) < 0
//                    && !eventService.hasOpenEvent(plant.getId(), "HEATING")) {
//                triggeredEvents.add(createEvent(plant, "HEATING",
//                        "Низкая температура: " + plant.getName()));
//                recommendationService.createIfAbsent(plant, 1L, "WARNING");
//
//                plant.setState(1);
//
//                System.out.println("trigger for temp");
//
//            }
//            else if (telemetry.getTemp().compareTo(minTemp) >= 0) {
//                eventService.resolveOpen(plant.getId(), "HEATING");
//
//            }
//            }
//
//        if (telemetry.getLight() != null) {
//            int minLight = getEffectiveLightMin(plant);
//            plant.setState(0);
//
//            if (telemetry.getLight() < minLight
//                    && !eventService.hasOpenEvent(plant.getId(), "LIGHT_CONTROL")) {
//                triggeredEvents.add(createEvent(plant, "LIGHT_CONTROL",
//                        "Недостаточно света: " + plant.getName()));
//                recommendationService.createIfAbsent(plant, 4L, "WARNING");
//                plant.setState(1);
//
//                System.out.println("trigger for light");
//            }
//            else if (telemetry.getLight() >= minLight) {
//                eventService.resolveOpen(plant.getId(), "LIGHT_CONTROL");
//
//            }
//        }
//
//        if (telemetry.getHumidity() != null) {
//            Integer minHumidity = getEffectiveHumMin(plant);
//            plant.setState(0);
//
//            if (minHumidity != null
//                    && telemetry.getHumidity() < minHumidity
//                    && !eventService.hasOpenEvent(plant.getId(), "HUMIDIFYING")) {
//                triggeredEvents.add(createEvent(plant, "HUMIDIFYING",
//                        "Низкая влажность воздуха: " + plant.getName()));
//                plant.setState(1);
//
//                System.out.println("trigger for humidity");
//            }
//            else if (minHumidity != null && telemetry.getHumidity() >= minHumidity) {
//                eventService.resolveOpen(plant.getId(), "HUMIDIFYING");
//            }
//        }
//
//        List<Event> savedEvents = eventService.saveAll(triggeredEvents);
//        for (Event event : savedEvents) {
//            webSocketService.sendEvent(event);
//            addCommand(plant, event);
//        }
//
//        Integer newState = savedEvents.isEmpty() ? 0 : 1;
//        plant.setState(newState);
//        plantService.updateState(plant.getId(), newState);
//    }

    public void check(Telemetry telemetry, PlantInstance plant, WebSocketService webSocketService) {
        List<Event> triggeredEvents = new ArrayList<>();
        boolean hasProblem = false;

        if (telemetry.getSoilMoisture() != null) {
            if (telemetry.getSoilMoisture() < getEffectiveSoilMoistureMin(plant)) {
                hasProblem = true;
                if (!eventService.hasOpenEvent(plant.getId(), "WATERING")) {
                    triggeredEvents.add(createEvent(plant, "WATERING",
                            "Низкая влажность почвы: " + plant.getName()));
                }
            } else {
                eventService.resolveOpen(plant.getId(), "WATERING");
            }
        }

        if (telemetry.getTemp() != null) {
            if (telemetry.getTemp().compareTo(getEffectiveTempMin(plant)) < 0) {
                hasProblem = true;
                if (!eventService.hasOpenEvent(plant.getId(), "HEATING")) {
                    triggeredEvents.add(createEvent(plant, "HEATING",
                            "Низкая температура: " + plant.getName()));
                }
            } else {
                eventService.resolveOpen(plant.getId(), "HEATING");
            }
        }

        if (telemetry.getLight() != null) {
            if (telemetry.getLight() < getEffectiveLightMin(plant)) {
                hasProblem = true;
                if (!eventService.hasOpenEvent(plant.getId(), "LIGHT_CONTROL")) {
                    triggeredEvents.add(createEvent(plant, "LIGHT_CONTROL",
                            "Недостаточно света: " + plant.getName()));
                }
            } else {
                eventService.resolveOpen(plant.getId(), "LIGHT_CONTROL");
            }
        }

        Integer minHumidity = getEffectiveHumMin(plant);
        if (telemetry.getHumidity() != null && minHumidity != null) {
            if (telemetry.getHumidity() < minHumidity) {
                hasProblem = true;
                if (!eventService.hasOpenEvent(plant.getId(), "HUMIDIFYING")) {
                    triggeredEvents.add(createEvent(plant, "HUMIDIFYING",
                            "Низкая влажность воздуха: " + plant.getName()));
                }
            } else {
                eventService.resolveOpen(plant.getId(), "HUMIDIFYING");
            }
        }

        List<Event> savedEvents = eventService.saveAll(triggeredEvents);
        for (Event event : savedEvents) {
            webSocketService.sendEvent(event);
            addCommand(plant, event);
        }

        Integer newState = hasProblem ? 1 : 0;
        plant.setState(newState);
        plantService.updateState(plant.getId(), newState);
    }


    private void addRecommendation(PlantInstance plant, Long msgId, String severity, WebSocketService webSocketService) {
        if (recommendationService.existsUnresolved(plant.getId(), msgId)) return;

        RecommendationMsg msg = recommendationMsgRepository.findById(msgId).orElse(null);
        if (msg == null) return;

        Recommendation rec = new Recommendation(plant, msg, severity);
        recommendationService.save(rec);
        webSocketService.sendRecommendation(plant, rec);

    }

    private void addCommand(PlantInstance plant, Event event) {
        Command command;
        System.out.println("addCommand called, event type = [" + event.getType() + "]");

        switch (event.getType()) {
            case "WATERING":
                command = commandService.createCommand(plant, event, "WATERING");
                System.out.println("wattering called for: " + plant.getName());

                break;
            case "HEATING":
                command = commandService.createCommand(plant, event, "HEATING");
                System.out.println("heating called for: " + plant.getName());
                break;
            case "HUMIDIFYING":
                command = commandService.createCommand(plant, event, "HUMIDIFYING");
                System.out.println("hum called for: " + plant.getName());

                break;
            case "LIGHT_CONTROL":
                command = commandService.createCommand(plant, event, "CURTAINS_OPEN");
                System.out.println("light called for: " + plant.getName());

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
            if (plant.getHeight() != null && plant.getPotSize() != null
                    && !recommendationService.existsUnresolved(plant.getId(), repotMsg.getId())) {
                BigDecimal currentHeight = plant.getHeight();
                BigDecimal potSize = BigDecimal.valueOf(plant.getPotSize());

                if (currentHeight.compareTo(potSize.multiply(BigDecimal.valueOf(0.9))) > 0) {
                    Recommendation rec = new Recommendation();
                    rec.setPlant(plant);
                    rec.setMessage(repotMsg);
                    rec.setSeverity("INFO");
                    rec.setCreatedAt(LocalDateTime.now());
                    rec.setResolved(false);
                    recommendationService.createPotSizeIfAbsent(plant, String.format("Растение достигло высоты %.1f см при размере горшка %d см. " +
                                    "Рекомендуется пересадка в горшок %d см.",
                            currentHeight, plant.getPotSize(), plant.getSpecies().getRecommendedPotSize().intValue()));

                    webSocketService.sendRecommendation(plant, rec);
                }
            }
        }
    }
}