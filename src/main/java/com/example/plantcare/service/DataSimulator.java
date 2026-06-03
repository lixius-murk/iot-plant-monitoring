package com.example.plantcare.service;

import com.example.plantcare.model.entity.PlantInstance;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.Random;


    @Service
    public class DataSimulator {

        private final Random random = new Random();

        private BigDecimal lastTemperature;
        private Integer lastSoilMoisture;
        private Integer lastHumidity;
        private BigDecimal lastEc;
        private final Integer maxMoi = 80;

        public BigDecimal generateTemperature(PlantInstance plant) {
            BigDecimal min = plant.getEffectiveTempMin();
            BigDecimal max = plant.getEffectiveTempMax();

            System.out.println("DEBUG: plant=" + plant.getName() +
                    ", minTemp=" + min +
                    ", maxTemp=" + max);

            if (min == null || max == null) {
                System.err.println("ERROR: min or max temperature is null!");
                min = BigDecimal.valueOf(18.0);
                max = BigDecimal.valueOf(28.0);
            }

            int hour = LocalTime.now().getHour();
            double timeFactor = Math.sin((hour - 6) * Math.PI / 12.0);
            BigDecimal base = min.add(max.subtract(min)
                    .multiply(BigDecimal.valueOf(0.5 + timeFactor * 0.3)));

            BigDecimal delta = BigDecimal.valueOf(random.nextDouble() * 2 - 1);
            BigDecimal next = base.add(delta);

            if (lastTemperature != null) {
                next = lastTemperature.add(next.subtract(lastTemperature).multiply(BigDecimal.valueOf(0.3)));
            }

            lastTemperature = next.setScale(1, RoundingMode.HALF_UP);
            System.out.println("generated temp: " + lastTemperature);

            return lastTemperature;
        }


        public Integer generateHumidity(PlantInstance plant) {
            int min = plant.getEffectiveHumMin();

            int prev = lastHumidity != null ? lastHumidity : (min + maxMoi) / 2;
            int next = prev + random.nextInt(7) - 3;
            next = Math.max(min, Math.min(maxMoi, next));
            System.out.println("generated hum: " + next);
            lastHumidity = next;
            return lastHumidity;
        }

        public Integer generateSoilMoisture(PlantInstance plant) {
            int min = plant.getEffectiveSoilMoistureMin();

            int prev = lastSoilMoisture != null ? lastSoilMoisture : (min + maxMoi) / 2;
            int next = (int) (prev * 0.98);
            next += random.nextInt(5) - 2;
            next = Math.max(min - 5, Math.min(maxMoi + 5, next));
            System.out.println("generated moist: " + next);

            lastSoilMoisture = next;
            return next;
        }

        public Integer generateLight() {
            int hour = LocalTime.now().getHour();
            if (hour < 7 || hour > 20) {
                return 50;
            }
            double factor = Math.max(0.1, 1 - Math.pow((hour - 13) / 7.0, 2));
            int light = (int) (500 + (12000 - 500) * factor);
            light = (int) (light * (0.8 + random.nextDouble() * 0.4));
            System.out.println("generated light: " + light);

            return Math.min(12000, Math.max(50, light));
        }

        public BigDecimal generateEc() {
            double prev = lastEc != null ? lastEc.doubleValue() : 1.2;
            double next = prev + (random.nextDouble() * 0.1 - 0.05);
            next = Math.max(0.3, Math.min(2.5, next));
            lastEc = BigDecimal.valueOf(next).setScale(2, RoundingMode.HALF_UP);
            System.out.println("generated ec: " + lastEc);

            return lastEc;
        }
    }