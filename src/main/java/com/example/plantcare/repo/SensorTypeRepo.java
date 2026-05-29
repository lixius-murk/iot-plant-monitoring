package com.example.plantcare.repo;

import com.example.plantcare.model.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SensorTypeRepo extends JpaRepository<SensorType, Long> {
    Optional<SensorType> findByName(String name);
}