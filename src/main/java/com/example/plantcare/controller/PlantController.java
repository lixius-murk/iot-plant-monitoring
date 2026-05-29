package com.example.plantcare.controller;

import com.example.plantcare.model.Dto;
import com.example.plantcare.model.entity.Plant;
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

    @GetMapping
    public ResponseEntity<List<Dto.PlantResponse>> getAllPlants() {
        return ResponseEntity.ok(
                plantService.getAllActive().stream().map(Dto.PlantResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dto.PlantResponse> getPlant(@PathVariable Long id) {
        return plantService.findById(id)
                .map(Dto.PlantResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Dto.PlantResponse> createPlant(@RequestBody Dto.PlantRequest req) {
        return ResponseEntity.ok(Dto.PlantResponse.from(plantService.create(req)));
    }

    @PutMapping("/{id}/settings")
    public ResponseEntity<Dto.PlantResponse> updateSettings(
            @PathVariable Long id, @RequestBody Dto.Settings settings) {
        return ResponseEntity.ok(Dto.PlantResponse.from(plantService.updateSettings(id, settings)));
    }

    @GetMapping("/{id}/telemetry/latest")
    public ResponseEntity<Dto.TelemetryData> getLatestTelemetry(@PathVariable Long id) {
        return telemetryService.getLatestByPlant(id)
                .map(Dto.TelemetryData::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }