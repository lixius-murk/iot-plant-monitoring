package com.example.plantcare.service;

import com.example.plantcare.model.*;
import com.example.plantcare.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepo plantRepo;
    private final TelemetryRepo telemetryRepo;
    private final EventRepo eventRepo;

    // CRUD
    public List<Plant> getAllPlants() {
        return plantRepo.findAll();
    }

    public Plant getPlant(Long id) {
        return plantRepo.findById(id).orElseThrow();
    }

    public Plant createPlant(Dto.PlantRequest request) {
        Plant plant = new Plant();
        plant.setName(request.getName());
        plant.setSpecies(request.getSpeciesId() == 1 ? "Монстера" :
                request.getSpeciesId() == 2 ? "Фикус" : "Кактус");

        // Default thresholds
        plant.setTempMin(BigDecimal.valueOf(18.0));
        plant.setTempMax(BigDecimal.valueOf(28.0));
        plant.setHumidityMin(40);
        plant.setSoilMoistureMin(40);
        plant.setLightMin(5000);

        return plantRepo.save(plant);
    }

    public void updateSettings(Long id, Dto.Settings settings) {
        Plant plant = getPlant(id);
        plant.setCustomTempMin(settings.getTempMin());
        plant.setCustomSoilMoistureMin(settings.getSoilMoistureMin());
        plant.setCustomLightMin(settings.getLightMin());
        plantRepo.save(plant);
    }

    // Telemetry
    public void saveTelemetry(Telemetry telemetry) {
        telemetryRepo.save(telemetry);
        checkThresholdsAndAct(telemetry);
    }

    public Dto.TelemetryData getLatestTelemetry(Long plantId) {
        return telemetryRepo.findFirstByPlantIdOrderByTimestampDesc(plantId)
                .map(t -> {
                    Dto.TelemetryData dto = new Dto.TelemetryData();
                    dto.setPlantId(plantId);
                    dto.setTemp(t.getTemperature());
                    dto.setHumidity(t.getHumidityAir());
                    dto.setSoilMoisture(t.getSoilMoisture());
                    dto.setLight(t.getLightLux());
                    dto.setTime(t.getTimestamp());
                    return dto;
                }).orElse(null);
    }

    // Логика принятия решений
    private void checkThresholdsAndAct(Telemetry telemetry) {
        Plant plant = getPlant(telemetry.getPlantId());
        boolean needAction = false;

        // Проверка влажности почвы
        if (telemetry.getSoilMoisture() != null &&
                telemetry.getSoilMoisture() < plant.getEffectiveSoilMoistureMin()) {

            Event event = new Event();
            event.setPlantId(plant.getId());
            event.setType("WATERING");
            event.setTrigger("AUTO");
            event.setAction("Включен полив на 5 секунд");
            eventRepo.save(event);

            needAction = true;
        }

        // Проверка температуры
        if (telemetry.getTemperature() != null &&
                telemetry.getTemperature().compareTo(plant.getEffectiveTempMin()) < 0) {

            Event event = new Event();
            event.setPlantId(plant.getId());
            event.setType("HEATING");
            event.setTrigger("AUTO");
            event.setAction("Включен обогрев");
            eventRepo.save(event);

            needAction = true;
        }

        // Проверка освещения
        if (telemetry.getLightLux() != null &&
                telemetry.getLightLux() < plant.getEffectiveLightMin()) {

            Event event = new Event();
            event.setPlantId(plant.getId());
            event.setType("LIGHT");
            event.setTrigger("AUTO");
            event.setAction("Открыть шторы/включить лампу");
            eventRepo.save(event);

            needAction = true;
        }

        // Обновляем состояние растения
        plant.setState(needAction ? 1 : 0);
        plantRepo.save(plant);
    }

    public List<Dto.EventData> getRecentEvents(Long plantId) {
        return eventRepo.findTop20ByPlantIdOrderByTimestampDesc(plantId)
                .stream()
                .map(e -> {
                    Dto.EventData dto = new Dto.EventData();
                    dto.setType(e.getType());
                    dto.setAction(e.getAction());
                    dto.setTime(e.getTimestamp());
                    return dto;
                }).collect(Collectors.toList());
    }
}