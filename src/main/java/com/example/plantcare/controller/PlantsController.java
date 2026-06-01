package com.example.plantcare.controller;


import com.example.plantcare.repo.PlantSpeciesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/species")
public class PlantsController {

    @Autowired
    private PlantSpeciesRepo speciesRepo;
    @GetMapping
    public List<Map<String, Object>> getAll() {
        return speciesRepo.findAll().stream()
                .map(p ->{
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    m.put("tempMin", p.getTempMin());
                    m.put("tempMax", p.getTempMax());
                    m.put("humMin", p.getHumMin());
                    m.put("humMax", p.getHumMax());
                    m.put("soilMoistureMin", p.getSoilMoistureMin());
                    m.put("lightMin", p.getLightMin());
                    return m;
                        }
                )
                .collect(Collectors.toList());
    }
}