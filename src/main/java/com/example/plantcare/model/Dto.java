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
        private BigDecimal tempMin;
        private Integer soilMoistureMin;
        private  Integer potSizeCm;
        private Integer lightMin;
        private LocalDateTime lastWateredAt;

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getSpecies() { return species; }
        public Integer getState() { return state; }
        public String getHealth() { return health; }
        public BigDecimal getHeightCm() { return heightCm; }
        public Integer getPotSizeCm() { return potSizeCm; }
        public LocalDateTime getLastWateredAt() { return lastWateredAt; }

        public void setId(Long id_) { id = id_; }
        public void setName(String name_) { name = name_; }
        public void setSpecies(String sp) {species = sp; }
        public void setState(Integer st) { state = st; }
        public void setHealth(String h) {health = h; }
        public void setSoilMoistureMin(Integer st) { soilMoistureMin = st; }
        public void setTempMin(BigDecimal st) { tempMin = st; }
        public void setLightMin(Integer st) { lightMin = st; }

        public void setHeightCm(BigDecimal h) {heightCm = h; }
        public void setPotSizeCm(Integer s) { potSizeCm = s; }
        public void setLastWateredAt(LocalDateTime t) { lastWateredAt = t; }


        public static PlantResponse from(PlantInstance plant) {
            PlantResponse response = new PlantResponse();
            response.setId(plant.getId());
            response.setName(plant.getName());
            response.setSpecies(plant.getSpecies() != null ? plant.getSpecies().getName() : null);
            response.setState(plant.getCurrentState());
            response.setHealth(plant.getHealthStatus());
            response.setHeightCm(plant.getCurrentHeightCm());
            response.setTempMin(plant.getEffectiveTempMin());
            response.setSoilMoistureMin(plant.getEffectiveSoilMoistureMin());
            response.setLightMin(plant.getEffectiveLightMin());
            response.setLastWateredAt(plant.getLastWateredAt());
            return response;
        }
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