package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlantSpeciesRepo extends JpaRepository<Plant, Long> {
    Optional<Plant> findByName(String name);
    boolean existsByName(String name);
}