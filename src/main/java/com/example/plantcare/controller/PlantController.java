package com.example.plantcare.controller;

import com.example.plantcare.model.Dto;
import com.example.plantcare.service.PlantService;
import com.example.plantcare.service.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @GetMapping
    public List<Dto.PlantResponse> getAllPlants() {
        return plantService.getAllPlants().stream()
                .map(Dto.PlantResponse::from)
                .toList();
    }
}