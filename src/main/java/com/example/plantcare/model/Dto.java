package com.example.plantcare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Dto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlantRequest {
        private String name;
        private Long speciesId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlantResponse {
        private Long id;
        private String name;
        private String species;
        private Integer state;
        private String health;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TelemetryData {
        private Long plantId;
        private BigDecimal temp;
        private Integer humidity;
        private Integer soilMoisture;
        private Integer light;
        private LocalDateTime time;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventData {
        private String type;
        private String action;
        private LocalDateTime time;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Command {
        private Long plantId;
        private String type;  // WATER, HEAT, LIGHT
        private Integer duration;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Settings {
        private BigDecimal tempMin;
        private Integer soilMoistureMin;
        private Integer lightMin;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Recommendation {
        private String message;
        private String severity;
    }
}