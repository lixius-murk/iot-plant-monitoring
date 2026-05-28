package com.example.plantcare.repo;

import com.example.plantcare.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelemetryRepo extends JpaRepository<Telemetry, Long> {
    Optional<Telemetry> findFirstByPlantIdOrderByTimestampDesc(Long plantId);
}