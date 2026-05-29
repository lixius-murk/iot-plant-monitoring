package com.example.plantcare.model;

import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.model.entity.Recommendation;
import com.example.plantcare.model.entity.Telemetry;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dto {

    public static class PlantRequest {
        private String name;
        private Long speciesId;
        private BigDecimal heightCm;
        private Integer potSizeCm;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Long getSpeciesId() { return speciesId; }
        public void setSpeciesId(Long speciesId) { this.speciesId = speciesId; }
        public BigDecimal getHeightCm() { return heightCm; }
        public void setHeightCm(BigDecimal heightCm) { this.heightCm = heightCm; }
        public Integer getPotSizeCm() { return potSizeCm; }
        public void setPotSizeCm(Integer potSizeCm) { this.potSizeCm = potSizeCm; }
    }

    public static class PlantResponse {
        private Long id;
        private String name;
        private String species;
        private Integer state;
        private String health;
        private BigDecimal heightCm;
        private Integer potSizeCm;
        private LocalDateTime lastWateredAt;

        public static PlantResponse from(PlantInstance p) {
            PlantResponse dto = new PlantResponse();
            dto.id = p.getId();
            dto.name = p.getName();
            dto.species = p.getSpecies() != null ? p.getSpecies().getName() : null;
            dto.state = p.getCurrentState();
            dto.health = p.getHealthStatus();
            dto.heightCm = p.getCurrentHeightCm();
            dto.potSizeCm = p.getCurrentPotSizeCm();
            dto.lastWateredAt = p.getLastWateredAt();
            return dto;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getSpecies() { return species; }
        public Integer getState() { return state; }
        public String getHealth() { return health; }
        public BigDecimal getHeightCm() { return heightCm; }
        public Integer getPotSizeCm() { return potSizeCm; }
        public LocalDateTime getLastWateredAt() { return lastWateredAt; }
    }

    public static class TelemetryData {
        private Long plantId;
        private BigDecimal temp;
        private Integer humidity;
        private Integer soilMoisture;
        private Integer light;
        private BigDecimal ec;
        private LocalDateTime time;

        public static TelemetryData from(Telemetry t) {
            TelemetryData dto = new TelemetryData();
            dto.plantId = t.getPlant().getId();
            dto.temp = t.getTemperature();
            dto.humidity = t.getHumidityAir();
            dto.soilMoisture = t.getSoilMoisture();
            dto.light = t.getLightLux();
            dto.ec = t.getEc();
            dto.time = t.getTimestamp();
            return dto;
        }

        public Long getPlantId() { return plantId; }
        public BigDecimal getTemp() { return temp; }
        public Integer getHumidity() { return humidity; }
        public Integer getSoilMoisture() { return soilMoisture; }
        public Integer getLight() { return light; }
        public BigDecimal getEc() { return ec; }
        public LocalDateTime getTime() { return time; }
    }

    public static class Settings {
        private BigDecimal tempMin;
        private BigDecimal tempMax;
        private Integer humMin;
        private Integer humMax;
        private Integer soilMoistureMin;
        private Integer soilMoistureMax;
        private Integer lightMin;

        public BigDecimal getTempMin() { return tempMin; }
        public void setTempMin(BigDecimal v) { this.tempMin = v; }
        public BigDecimal getTempMax() { return tempMax; }
        public void setTempMax(BigDecimal v) { this.tempMax = v; }
        public Integer getHumMin() { return humMin; }
        public void setHumMin(Integer v) { this.humMin = v; }
        public Integer getHumMax() { return humMax; }
        public void setHumMax(Integer v) { this.humMax = v; }
        public Integer getSoilMoistureMin() { return soilMoistureMin; }
        public void setSoilMoistureMin(Integer v) { this.soilMoistureMin = v; }
        public Integer getSoilMoistureMax() { return soilMoistureMax; }
        public void setSoilMoistureMax(Integer v) { this.soilMoistureMax = v; }
        public Integer getLightMin() { return lightMin; }
        public void setLightMin(Integer v) { this.lightMin = v; }
    }

    public static class RecommendationData {
        private Long id;
        private String type;
        private String message;
        private String severity;
        private Boolean resolved;
        private LocalDateTime createdAt;

        public static RecommendationData from(Recommendation r) {
            RecommendationData dto = new RecommendationData();
            dto.id = r.getId();
            dto.type = r.getRecommendationType();
            dto.message = r.getMessage();
            dto.severity = r.getSeverity();
            dto.resolved = r.getIsResolved();
            dto.createdAt = r.getCreatedAt();
            return dto;
        }

        public Long getId() { return id; }
        public String getType() { return type; }
        public String getMessage() { return message; }
        public String getSeverity() { return severity; }
        public Boolean getResolved() { return resolved; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }
}