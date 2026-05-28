package com.example.plantcare.controller;

import com.example.plantcare.model.Dto;
import com.example.plantcare.model.Plant;
import com.example.plantcare.service.DataSimulator;
import com.example.plantcare.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlantController {

    private final PlantService plantService;
    private final DataSimulator simulator;

    public PlantController(PlantService plantService, DataSimulator simulator) {
        this.plantService = plantService;
        this.simulator = simulator;
    }

    @GetMapping("/plants")
    public ResponseEntity<List<Plant>> getAllPlants() {
        return ResponseEntity.ok(plantService.getAllPlants());
    }

    @GetMapping("/plants/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getPlant(id));
    }

    @PostMapping("/plants")
    public ResponseEntity<Plant> createPlant(@RequestBody Dto.PlantRequest request) {
        return ResponseEntity.ok(plantService.createPlant(request));
    }

    @PutMapping("/plants/{id}/settings")
    public ResponseEntity<Void> updateSettings(@PathVariable Long id,
                                               @RequestBody Dto.Settings settings) {
        plantService.updateSettings(id, settings);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/plants/{id}/telemetry/latest")
    public ResponseEntity<Dto.TelemetryData> getLatestTelemetry(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getLatestTelemetry(id));
    }

    @GetMapping("/plants/{id}/events")
    public ResponseEntity<List<Dto.EventData>> getEvents(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getRecentEvents(id));
    }

    @PostMapping("/plants/{id}/water")
    public ResponseEntity<Dto.Command> manualWater(@PathVariable Long id) {
        simulator.applyWatering(id);

        Dto.Command command = new Dto.Command();
        command.setPlantId(id);
        command.setType("WATER");
        command.setDuration(5);

        return ResponseEntity.ok(command);
    }

    @PostMapping("/simulate/{id}")
    public ResponseEntity<Void> simulate(@PathVariable Long id) {
        simulator.simulateAndSend(id);
        return ResponseEntity.ok().build();
    }
}