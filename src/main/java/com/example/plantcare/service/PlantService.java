package com.example.plantcare.service;

import com.example.plantcare.model.Dto;
import com.example.plantcare.model.entity.Plant;
import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.repo.PlantInstanceRepository;
import com.example.plantcare.repo.PlantSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    @Autowired
    private PlantInstanceRepository plantRepo;

    @Autowired
    private PlantSpeciesRepository speciesRepo;

    public List<PlantInstance> getAllActive() {
        return plantRepo.findByIsActiveTrue();
    }

    public Optional<PlantInstance> findById(Long id) {
        return plantRepo.findById(id);
    }

    public PlantInstance create(Dto.PlantRequest req) {
        Plant species = speciesRepo.findById(req.getSpeciesId())
                .orElseThrow(() -> new IllegalArgumentException("Species not found: " + req.getSpeciesId()));

        PlantInstance p = new PlantInstance();
        p.setName(req.getName());
        p.setSpecies(species);
        p.setCurrentHeightCm(req.getHeightCm());
        p.setCurrentPotSizeCm(req.getPotSizeCm());
        return plantRepo.save(p);
    }

    public PlantInstance updateSettings(Long id, Dto.Settings s) {
        PlantInstance p = plantRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plant not found: " + id));

        p.setCustomTempMin(s.getTempMin());
        p.setCustomTempMax(s.getTempMax());
        p.setCustomHumMin(s.getHumMin());
        p.setCustomHumMax(s.getHumMax());
        p.setCustomSoilMoistureMin(s.getSoilMoistureMin());
        p.setCustomSoilMoistureMax(s.getSoilMoistureMax());
        p.setCustomLightMin(s.getLightMin());
        p.setUpdatedAt(LocalDateTime.now());
        return plantRepo.save(p);
    }

    public void delete(Long id) {
        plantRepo.findById(id).ifPresent(p -> {
            p.setIsActive(false);
            plantRepo.save(p);
        });
    }

    public long countActive() {
        return plantRepo.countByIsActiveTrue();
    }

    public long countByState(Integer state) {
        return plantRepo.countByCurrentState(state);
    }

    public long countByHealthStatus(String status) {
        return plantRepo.findByHealthStatus(status).size();
    }
}