package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Long> {
    List<Sensor> findByIsActiveTrue();

}
