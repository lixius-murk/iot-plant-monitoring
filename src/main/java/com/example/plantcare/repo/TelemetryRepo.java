package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetryRepo extends JpaRepository<Telemetry, Long> {

    @Query("SELECT t FROM Telemetry t WHERE t.plant.id = :plantId ORDER BY t.timestamp DESC LIMIT 1")
    Optional<Telemetry> findLatestByPlantId(@Param("plantId") Long plantId);

    List<Telemetry> findByPlant_IdAndTimestampBetweenOrderByTimestampAsc(
            Long plantId, LocalDateTime from, LocalDateTime to);
}