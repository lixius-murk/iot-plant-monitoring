package com.example.plantcare.repo;

import com.example.plantcare.model.entity.PlantSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantSensorRepo extends JpaRepository<PlantSensor, Long> {
}