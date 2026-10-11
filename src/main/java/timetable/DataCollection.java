package timetable;

import model.entity.PlantInstance;
import model.entity.Sensor;
import model.entity.Telemetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.*;
import simulator.DataSimulator;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "app.simulation.enabled", havingValue = "true", matchIfMissing = true)
public class DataCollection {

        @Autowired(required = false)
        private PlantService plantService;

        @Autowired(required = false)
        private SensorService sensorService;

        @Autowired
        private DataSimulator dataSimulator;


        @Autowired(required = false)
        private TelemetryService telemetryService;

        @Autowired
        private LogicEngine logicEngine;

        @Autowired(required = false)
        private WebSocketService webSocketService;

    @Scheduled(fixedDelayString = "${app.simulation.interval-seconds}", timeUnit = TimeUnit.SECONDS)
    public void collectData() {
        List<PlantInstance> activePlants = plantService.getAllActive();

        for (PlantInstance plant : activePlants) {
            List<Sensor> sensors = sensorService.getSensorsByPlant(plant.getIdPlant());
            if (sensors.isEmpty()) continue;

            Telemetry telemetry = dataSimulator.generateTelemetry(plant, sensors.get(0));

            telemetryService.save(telemetry);
            webSocketService.sendTelemetry(telemetry);
            logicEngine.check(telemetry, plant, webSocketService);
        }
    }
    }
