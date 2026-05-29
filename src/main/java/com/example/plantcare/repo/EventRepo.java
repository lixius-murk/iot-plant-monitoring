package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findTop20ByPlantIdOrderByTimestampDesc(Long plantId);
}