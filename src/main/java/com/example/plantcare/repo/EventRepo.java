package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {
    List<Event> findByPlant_IdOrderByTimestampDesc(Long plantId);

    @Query("SELECT e FROM Event e ORDER BY e.timestamp DESC LIMIT 20")
    List<Event> findRecent();
}