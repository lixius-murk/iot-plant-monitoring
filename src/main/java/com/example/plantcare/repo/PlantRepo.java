package com.example.plantcare.repo;
import com.example.plantcare.model.Plant;
import com.example.plantcare.model.Telemetry;
import com.example.plantcare.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


public class PlantRepo {


    @Repository
    public interface PlantRepository extends JpaRepository<Plant, Long> {

        List<Plant> findByState(Integer state);

        List<Plant> findByHealth(String health);
    }

    @Repository
    interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
        List<Telemetry> findTop10ByPlantIdOrderByTimestampDesc(Long plantId);

        Optional<Telemetry> findFirstByPlantIdOrderByTimestampDesc(Long plantId);
    }

    @Repository
    interface EventRepository extends JpaRepository<Event, Long> {
        List<Event> findTop20ByPlantIdOrderByTimestampDesc(Long plantId);
    }

}