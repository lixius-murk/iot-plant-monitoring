package com.example.plantcare.service;

import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.model.entity.Recommendation;
import com.example.plantcare.model.entity.Telemetry;
import com.example.plantcare.repo.PlantInstanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class LogicEngine {

    @Autowired
    private EventService eventService;

    @Autowired
    private RecoService recommendationService;

    @Autowired
    private PlantInstanceRepo plantRepo;

    public void evaluate(Telemetry telemetry, PlantInstance plant) {
        boolean needsAction = false;

        if (telemetry.getSoilMoisture() != null) {
            int min = plant.getEffectiveSoilMoistureMin();
            if (telemetry.getSoilMoisture() < min) {
                eventService.createAutoEvent(plant, "WATERING",
                        "Low soil moisture: " + telemetry.getSoilMoisture() + "%");
                needsAction = true;
            }
        }

        if (telemetry.getTemperature() != null) {
            BigDecimal min = plant.getEffectiveTempMin();
            BigDecimal max = plant.getEffectiveTempMax();
            if (telemetry.getTemperature().compareTo(min) < 0) {
                eventService.createAutoEvent(plant, "HEATING",
                        "Low temp: " + telemetry.getTemperature() + "°C");
                needsAction = true;
            } else if (telemetry.getTemperature().compareTo(max) > 0) {
                eventService.createAutoEvent(plant, "COOLING",
                        "High temp: " + telemetry.getTemperature() + "°C");
                needsAction = true;
            }
        }

        if (telemetry.getLightLux() != null) {
            int min = plant.getEffectiveLightMin();
            if (telemetry.getLightLux() < min) {
                eventService.createAutoEvent(plant, "LIGHT_CONTROL",
                        "Low light: " + telemetry.getLightLux() + " lux");
                needsAction = true;
            }
        }

        if (telemetry.getEc() != null
                && telemetry.getEc().compareTo(BigDecimal.valueOf(0.8)) < 0) {
            Recommendation rec = new Recommendation();
            rec.setPlant(plant);
            rec.setRecommendationType("FERTILIZE");
            rec.setMessage("Low EC=" + telemetry.getEc() + " mS/cm. Fertilization recommended.");
            rec.setSeverity("WARNING");
            recommendationService.save(rec);
        }

        plant.setCurrentState(needsAction ? 1 : 0);
        plant.setHealthStatus(needsAction ? "NEEDS_ATTENTION" : "HEALTHY");
        plantRepo.save(plant);
    }
}