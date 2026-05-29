package com.example.plantcare.scedule;


import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.model.entity.Sensor;
import com.example.plantcare.model.entity.Telemetry;
import com.example.plantcare.service.LogicEngine;
import com.example.plantcare.service.PlantService;
import com.example.plantcare.service.SensorService;
import com.example.plantcare.service.TelemetryService;
import com.example.plantcare.service.DataSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataCollection {

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
            for (Sensor sensor : sensors) {
                Telemetry telemetry = dataSimulator.generateTelemetry(plant, sensor);
                telemetryService.save(telemetry);
                logicEngine.evaluate(telemetry, plant);
            }
        }
    }
}