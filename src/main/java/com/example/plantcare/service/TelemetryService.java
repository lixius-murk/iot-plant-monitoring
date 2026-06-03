package com.example.plantcare.service;

import com.example.plantcare.model.entity.Telemetry;
import com.example.plantcare.repo.TelemetryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TelemetryService {

    @Autowired
    private TelemetryRepo telemetryRepo;

    public Telemetry save(Telemetry telemetry) {
        System.out.println("Saving telemetry: temp=" + telemetry.getTemperature() +
                ", humidity=" + telemetry.getHumidityAir() +
                ", soil=" + telemetry.getSoilMoisture() +
                ", light=" + telemetry.getLightLux() +
                ", ec=" + telemetry.getEc());
        return telemetryRepo.save(telemetry);
    }
    public Optional<Telemetry> getLatestByPlant(Long plantId) {
        return telemetryRepo.findLatestByPlantId(plantId);
    }

    public List<Telemetry> getHistory(Long plantId, int hours) {
        LocalDateTime from = LocalDateTime.now().minusHours(hours);
        return telemetryRepo.findByPlant_IdAndTimestampBetweenOrderByTimestampAsc(
                plantId, from, LocalDateTime.now());
    }
}