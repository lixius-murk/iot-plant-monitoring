package com.example.plantcare.scedule;

import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.model.entity.Sensor;
import com.example.plantcare.model.entity.Telemetry;
import com.example.plantcare.service.DataSimulator;
import com.example.plantcare.service.LogicEngine;
import com.example.plantcare.service.PlantService;
import com.example.plantcare.service.SensorService;
import com.example.plantcare.service.TelemetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataCollection {

    private static final Logger log = LoggerFactory.getLogger(DataCollection.class);

    @Autowired
    private PlantService plantService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DataSimulator dataSimulator;

    @Autowired
    private TelemetryService telemetryService;

    @Autowired
    private LogicEngine logicEngine;

    @Scheduled(fixedDelay = 30000)
    public void collectData() {
        List<PlantInstance> plants = plantService.getAllActive();

        for (PlantInstance plant : plants) {
            List<Sensor> sensors = sensorService.getSensorsByPlant(plant.getId());
            Telemetry telemetry = new Telemetry();
            telemetry.setPlant(plant);
            telemetry.setTimestamp(java.time.LocalDateTime.now());

            for (Sensor sensor : sensors) {
                String sensorType = sensor.getSensorType().getName().toUpperCase().trim();
                System.out.println("Sensor type: '" + sensorType + "'");

                switch (sensorType) {
                    case "TEMPERATURE":
                        BigDecimal temp = dataSimulator.generateTemperature(plant);
                        System.out.println("Generated temp: " + temp);
                        telemetry.setTemperature(temp);
                        break;
                    case "HUMIDITY_AIR":
                        Integer hum = dataSimulator.generateHumidity(plant);
                        System.out.println("Generated humidity: " + hum);
                        telemetry.setHumidityAir(hum);
                        break;
                    case "SOIL_MOISTURE":
                        Integer soil = dataSimulator.generateSoilMoisture(plant);
                        System.out.println("Generated soil moisture: " + soil);
                        telemetry.setSoilMoisture(soil);
                        break;
                    case "LIGHT":
                        Integer light = dataSimulator.generateLight();
                        System.out.println("Generated light: " + light);
                        telemetry.setLightLux(light);
                        break;
                    case "EC":
                        BigDecimal ec = dataSimulator.generateEc();
                        System.out.println("Generated EC: " + ec);
                        telemetry.setEc(ec);
                        break;
                }
            }

            System.out.println("Final telemetry before save: temp=" + telemetry.getTemperature() +
                    ", humidity=" + telemetry.getHumidityAir() +
                    ", soil=" + telemetry.getSoilMoisture() +
                    ", light=" + telemetry.getLightLux() +
                    ", ec=" + telemetry.getEc());

            Telemetry saved = telemetryService.save(telemetry);
            logicEngine.evaluate(saved, plant);
        }
    }
}

