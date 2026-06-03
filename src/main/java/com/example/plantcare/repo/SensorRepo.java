package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Long> {

    @Query("SELECT s FROM Sensor s JOIN s.plantSensors ps WHERE ps.plant.id = :plantId AND ps.isActive = true")
    List<Sensor> findActiveByPlantId(@Param("plantId") Long plantId);
}
