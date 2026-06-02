package com.example.plantcare.repo;

import com.example.plantcare.model.entity.PlantInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantInstanceRepo extends JpaRepository<PlantInstance, Long> {
    List<PlantInstance> findByIsActiveTrue();
    List<PlantInstance> findByCurrentState(Integer state);
    List<PlantInstance> findByHealthStatus(String healthStatus);
    long countByIsActiveTrue();
    long countByCurrentState(Integer state);
}