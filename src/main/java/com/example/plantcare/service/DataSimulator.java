package com.example.plantcare.service;

import com.example.plantcare.model.entity.Telemetry;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataSimulator {

    private final PlantService plantService;
    private final Random random = new Random();
    private final ConcurrentHashMap<Long, DeviceState> states = new ConcurrentHashMap<>();

    public DataSimulator(PlantService plantService) {
        this.plantService = plantService;
    }

    public void simulateAndSend(Long plantId) {
        plantService.getPlant(plantId);
        DeviceState state = states.computeIfAbsent(plantId, k -> new DeviceState());

        Telemetry telemetry = new Telemetry();
        telemetry.setPlantId(plantId);

        int hour = LocalDateTime.now().getHour();
        //generating data that consideting time
        double dayFactor = Math.sin((hour - 6) * Math.PI / 12);
        double baseTemp = 22 + dayFactor * 5;
        telemetry.setTemperature(BigDecimal.valueOf(baseTemp + (random.nextDouble())));

        telemetry.setHumidityAir(45 + random.nextInt(40));

        int moisture = state.lastMoisture > 0 ? state.lastMoisture : 50;
        moisture = (int) (moisture * 0.98);
        telemetry.setSoilMoisture(moisture);
        state.lastMoisture = moisture;

        if (hour < 7 || hour > 20) {
            telemetry.setLightLux(50);
        } else {
            telemetry.setLightLux(Math.max(100, (int)dayFactor + random.nextInt(1000) - 500));
        }

        telemetry.setEc(BigDecimal.valueOf(0.8 + random.nextDouble() * 0.8));
        telemetry.setTimestamp(LocalDateTime.now());

        plantService.saveTelemetry(telemetry);
    }

    public void applyWatering(Long plantId) {
        DeviceState state = states.computeIfAbsent(plantId, k -> new DeviceState());
        state.wateringActive = true;
        state.wateringTurns+=1;
    }

    private static class DeviceState {
        int lastMoisture = 50;
        boolean wateringActive = false;
        int wateringTurns = 0;
    }
}