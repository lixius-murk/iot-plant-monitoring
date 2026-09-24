package service;

import model.entity.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import simulator.DataSimulator;

import java.util.Random;


@Service
public class MockDeviceGateway {

    private final Random random = new Random();

    @Autowired(required = false)
    private CommandService commandService;
    @Autowired
    private DataSimulator dataSimulator;

    @Async
    public void sendCommand(Command command) {
        try {
            // simulate latency
            Thread.sleep(200 );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        commandService.acknowledge(command.getIdCommand());
        switch (command.getCommandType()) {
            case "WATERING":
                dataSimulator.startWatering(command.getPlant().getId());
                break;
            case "HEATING":
                dataSimulator.startHeating(command.getPlant().getId());
                System.out.println("started for: " + command.getPlant().getName());

                break;
//            case "CURTAINS_OPEN":
//                dataSimulator.startLight(command.getPlant().getId());
//                break;
            case "HUMIDIFYING":
                dataSimulator.startHumidifying(command.getPlant().getId());
                break;
        }
    }
}
