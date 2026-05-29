package com.example.plantcare.service;

import com.example.plantcare.model.entity.Sensor;
import com.example.plantcare.repo.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepo sensorRepo;

    public List<Sensor> getSensorsByPlant(Long plantId) {
        return sensorRepo.findAll().stream()
                .filter(s -> s.getIsActive() && s.getPlantSensors().stream()
                        .anyMatch(ps -> ps.getIsActive()
                                && ps.getPlant().getId().equals(plantId)))
                .toList();
    }

    public Sensor save(Sensor s) {
        return sensorRepo.save(s);
    }
}