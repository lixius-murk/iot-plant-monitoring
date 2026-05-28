package com.example.plantcare.controller;

import com.example.plantcare.model.*;
import com.example.plantcare.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;
    private final DataSimulator simulator;

    // Растения
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

    // Настройки
    @PutMapping("/plants/{id}/settings")
    public ResponseEntity<Void> updateSettings(@PathVariable Long id,
                                               @RequestBody Dto.Settings settings) {
        plantService.updateSettings(id, settings);
        return ResponseEntity.ok().build();
    }

    // Телеметрия
    @GetMapping("/plants/{id}/telemetry/latest")
    public ResponseEntity<Dto.TelemetryData> getLatestTelemetry(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getLatestTelemetry(id));
    }

    // События
    @GetMapping("/plants/{id}/events")
    public ResponseEntity<List<Dto.EventData>> getEvents(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getRecentEvents(id));
    }

    // Ручное управление
    @PostMapping("/plants/{id}/water")
    public ResponseEntity<Dto.Command> manualWater(@PathVariable Long id) {
        simulator.applyWatering(id);

        Dto.Command command = new Dto.Command();
        command.setPlantId(id);
        command.setType("WATER");
        command.setDuration(5);

        return ResponseEntity.ok(command);
    }

    // Симуляция (запустить генерацию данных)
    @PostMapping("/simulate/{id}")
    public ResponseEntity<Void> simulate(@PathVariable Long id) {
        simulator.simulateAndSend(id);
        return ResponseEntity.ok().build();
    }
}