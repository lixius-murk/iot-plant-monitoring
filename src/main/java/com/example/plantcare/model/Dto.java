package com.example.plantcare.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dto {

    // PlantRequest
    public static class PlantRequest {
        private String name;
        private Long speciesId;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Long getSpeciesId() { return speciesId; }
        public void setSpeciesId(Long speciesId) { this.speciesId = speciesId; }
    }

    // PlantResponse
    public static class PlantResponse {
        private Long id;
        private String name;
        private String species;
        private Integer state;
        private String health;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSpecies() { return species; }
        public void setSpecies(String species) { this.species = species; }
        public Integer getState() { return state; }
        public void setState(Integer state) { this.state = state; }
        public String getHealth() { return health; }
        public void setHealth(String health) { this.health = health; }
    }

    // TelemetryData
    public static class TelemetryData {
        private Long plantId;
        private BigDecimal temp;
        private Integer humidity;
        private Integer soilMoisture;
        private Integer light;
        private LocalDateTime time;

        public Long getPlantId() { return plantId; }
        public void setPlantId(Long plantId) { this.plantId = plantId; }
        public BigDecimal getTemp() { return temp; }
        public void setTemp(BigDecimal temp) { this.temp = temp; }
        public Integer getHumidity() { return humidity; }
        public void setHumidity(Integer humidity) { this.humidity = humidity; }
        public Integer getSoilMoisture() { return soilMoisture; }
        public void setSoilMoisture(Integer soilMoisture) { this.soilMoisture = soilMoisture; }
        public Integer getLight() { return light; }
        public void setLight(Integer light) { this.light = light; }
        public LocalDateTime getTime() { return time; }
        public void setTime(LocalDateTime time) { this.time = time; }
    }

    // EventData
    public static class EventData {
        private String type;
        private String action;
        private LocalDateTime time;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public LocalDateTime getTime() { return time; }
        public void setTime(LocalDateTime time) { this.time = time; }
    }

    // Command
    public static class Command {
        private Long plantId;
        private String type;
        private Integer duration;

        public Long getPlantId() { return plantId; }
        public void setPlantId(Long plantId) { this.plantId = plantId; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
    }

    // Settings
    public static class Settings {
        private BigDecimal tempMin;
        private Integer soilMoistureMin;
        private Integer lightMin;

        public BigDecimal getTempMin() { return tempMin; }
        public void setTempMin(BigDecimal tempMin) { this.tempMin = tempMin; }
        public Integer getSoilMoistureMin() { return soilMoistureMin; }
        public void setSoilMoistureMin(Integer soilMoistureMin) { this.soilMoistureMin = soilMoistureMin; }
        public Integer getLightMin() { return lightMin; }
        public void setLightMin(Integer lightMin) { this.lightMin = lightMin; }
    }

    // Recommendation
    public static class Recommendation {
        private String message;
        private String severity;

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
    }
}