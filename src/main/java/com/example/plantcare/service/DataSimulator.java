package com.example.plantcare.service;

import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.model.entity.Sensor;
import com.example.plantcare.model.entity.Telemetry;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DataSimulator {

    private int maxHum = 80;
    private final Random random = new Random();
    private final Map<Long, DeviceState> deviceStates = new ConcurrentHashMap<>();

    public Telemetry generateTelemetry(PlantInstance plant, Sensor sensor) {
        DeviceState state = deviceStates.computeIfAbsent(
                plant.getId(), k -> new DeviceState());

        Telemetry t = new Telemetry();
        t.setPlant(plant);
        t.setSensor(sensor);
        t.setTimestamp(LocalDateTime.now());

        String type = sensor.getSensorType().getName();
        switch (type) {
            case "TEMPERATURE" -> t.setTemperature(generateTemperature(plant, state));
            case "HUMIDITY_AIR"-> t.setHumidityAir(generateHumidity(plant, state));
            case "SOIL_MOISTURE"-> t.setSoilMoisture(generateSoilMoisture(plant, state));
            case "LIGHT"-> t.setLightLux(generateLight(state));
            case "EC" -> t.setEc(generateEc(state));
        }
        return t;
    }

    private BigDecimal generateTemperature(PlantInstance plant, DeviceState state) {
        BigDecimal min = plant.getEffectiveTempMin();
        BigDecimal max = plant.getEffectiveTempMax();
        int hour = LocalTime.now().getHour();
        double timeFactor = Math.sin((hour - 6) * Math.PI / 12.0);
        BigDecimal base = min.add(max.subtract(min)
                .multiply(BigDecimal.valueOf(0.5 + timeFactor * 0.3)));
        BigDecimal delta = BigDecimal.valueOf(random.nextDouble() * 3 - 1.5);
        BigDecimal next = base.add(delta);
        if (state.lastTemperature != null) {
            next = state.lastTemperature.add(
                    next.subtract(state.lastTemperature).multiply(BigDecimal.valueOf(0.3)));
        }
        state.lastTemperature = next.setScale(1, RoundingMode.HALF_UP);
        return state.lastTemperature;
    }

    private Integer generateHumidity(PlantInstance plant, DeviceState state) {
        int min = plant.getEffectiveHumMin();
        int prev = state.lastHumidity != null ? state.lastHumidity : (min + maxHum) / 2;
        int next = prev + random.nextInt(5) - 2;
        next = Math.max(min, Math.min(maxHum, next));
        state.lastHumidity = next;
        return next;
    }

    private Integer generateSoilMoisture(PlantInstance plant, DeviceState state) {
        int min = plant.getEffectiveSoilMoistureMin();
        int prev = state.lastSoilMoisture != null ? state.lastSoilMoisture : (min + maxHum) / 2;
        int next = (int) (prev * 0.98);
        next += random.nextInt(5) - 2;
        next = Math.max(min - 10, Math.min(maxHum, next)); // allow going below min to trigger events
        state.lastSoilMoisture = next;
        return next;
    }

    private Integer generateLight(DeviceState state) {
        int hour = LocalTime.now().getHour();
        if (hour < 7 || hour > 20) return 50;
        double factor = Math.max(0.1, 1 - Math.pow((hour - 13) / 7.0, 2));
        int light = (int) (500 + (12000 - 500) * factor);
        light = (int) (light * (0.8 + random.nextDouble() * 0.4));
        return Math.min(12000, Math.max(50, light));
    }

    private BigDecimal generateEc(DeviceState state) {
        double prev = state.lastEc != null ? state.lastEc.doubleValue() : 1.2;
        double next = prev + (random.nextDouble() * 0.1 - 0.05);
        next = Math.max(0.3, Math.min(2.5, next));
        state.lastEc = BigDecimal.valueOf(next).setScale(2, RoundingMode.HALF_UP);
        return state.lastEc;
    }

    private static class DeviceState {
        BigDecimal lastTemperature;
        Integer lastSoilMoisture;
        Integer lastHumidity;
        BigDecimal lastEc;
    }
}