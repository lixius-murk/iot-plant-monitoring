package com.example.plantcare.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "plant_instances")
public class PlantInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plant")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_species", nullable = false)
    private Plant species;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlantSensor> plantSensors = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "planted_at")
    private LocalDateTime plantedAt = LocalDateTime.now();

    @Column(name = "current_height_cm")
    private BigDecimal currentHeightCm;

    @Column(name = "current_pot_size_cm")
    private Integer currentPotSizeCm;

    @Column(name = "current_state")
    private Integer currentState = 0;

    @Column(name = "health_status")
    private String healthStatus = "HEALTHY";

    @Column(name = "custom_temp_min")
    private BigDecimal customTempMin;

    @Column(name = "custom_temp_max")
    private BigDecimal customTempMax;

    @Column(name = "custom_hum_min")
    private Integer customHumMin;

    @Column(name = "custom_soil_moisture_min")
    private Integer customSoilMoistureMin;

    @Column(name = "custom_light_min")
    private Integer customLightMin;

    @Column(name = "last_watered_at")
    private LocalDateTime lastWateredAt;

    @Column(name = "last_fertilized_at")
    private LocalDateTime lastFertilizedAt;

    @Column(name = "last_check_at")
    private LocalDateTime lastCheckAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public List<Sensor> getActiveSensors() {
        return plantSensors.stream()
                .filter(PlantSensor::getIsActive)
                .map(PlantSensor::getSensor)
                .collect(Collectors.toList());
    }

    public List<Sensor> getSensorsByType(String sensorTypeName) {
        return plantSensors.stream()
                .filter(ps -> ps.getIsActive())
                .map(PlantSensor::getSensor)
                .filter(s -> s.getSensorType().getName().equals(sensorTypeName))
                .collect(Collectors.toList());
    }

    public Optional<Sensor> getSoilMoistureSensor() {
        return getSensorsByType("SOIL_MOISTURE").stream().findFirst();
    }

    public Optional<Sensor> getTemperatureSensor() {
        return getSensorsByType("TEMPERATURE").stream().findFirst();
    }

    public Optional<Sensor> getLightSensor() {
        return getSensorsByType("LIGHT").stream().findFirst();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Plant getSpecies() { return species; }
    public void setSpecies(Plant species) { this.species = species; }

    public List<PlantSensor> getPlantSensors() { return plantSensors; }
    public void setPlantSensors(List<PlantSensor> plantSensors) { this.plantSensors = plantSensors; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getPlantedAt() { return plantedAt; }
    public void setPlantedAt(LocalDateTime plantedAt) { this.plantedAt = plantedAt; }

    public BigDecimal getCurrentHeightCm() { return currentHeightCm; }
    public void setCurrentHeightCm(BigDecimal currentHeightCm) { this.currentHeightCm = currentHeightCm; }

    public Integer getCurrentPotSizeCm() { return currentPotSizeCm; }
    public void setCurrentPotSizeCm(Integer currentPotSizeCm) { this.currentPotSizeCm = currentPotSizeCm; }

    public Integer getCurrentState() { return currentState; }
    public void setCurrentState(Integer currentState) { this.currentState = currentState; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public BigDecimal getCustomTempMin() { return customTempMin; }
    public void setCustomTempMin(BigDecimal customTempMin) { this.customTempMin = customTempMin; }

    public BigDecimal getCustomTempMax() { return customTempMax; }
    public void setCustomTempMax(BigDecimal customTempMax) { this.customTempMax = customTempMax; }

    public Integer getCustomHumMin() { return customHumMin; }
    public void setCustomHumMin(Integer customHumMin) { this.customHumMin = customHumMin; }

    public Integer getCustomSoilMoistureMin() { return customSoilMoistureMin; }
    public void setCustomSoilMoistureMin(Integer customSoilMoistureMin) { this.customSoilMoistureMin = customSoilMoistureMin; }

    public Integer getCustomLightMin() { return customLightMin; }
    public void setCustomLightMin(Integer customLightMin) { this.customLightMin = customLightMin; }

    public LocalDateTime getLastWateredAt() { return lastWateredAt; }
    public void setLastWateredAt(LocalDateTime lastWateredAt) { this.lastWateredAt = lastWateredAt; }

    public LocalDateTime getLastFertilizedAt() { return lastFertilizedAt; }
    public void setLastFertilizedAt(LocalDateTime lastFertilizedAt) { this.lastFertilizedAt = lastFertilizedAt; }

    public LocalDateTime getLastCheckAt() { return lastCheckAt; }
    public void setLastCheckAt(LocalDateTime lastCheckAt) { this.lastCheckAt = lastCheckAt; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public BigDecimal getEffectiveTempMin() {
        if (customTempMin != null) {
            return customTempMin;
        }
        if (species != null && species.getTempMin() != null) {
            return species.getTempMin();
        }
        return BigDecimal.valueOf(18.0);   
    }

    public BigDecimal getEffectiveTempMax() {
        if (customTempMax != null) {
            return customTempMax;
        }
        if (species != null && species.getTempMax() != null) {
            return species.getTempMax();
        }
        return BigDecimal.valueOf(28.0);   
    }

    public Integer getEffectiveHumMin() {
        if (customHumMin != null) {
            return customHumMin;
        }
        if (species != null && species.getHumMin() != null) {
            return species.getHumMin();
        }
        return 40; 
    }

    public Integer getEffectiveSoilMoistureMin() {
        if (customSoilMoistureMin != null) {
            return customSoilMoistureMin;
        }
        if (species != null && species.getSoilMoistureMin() != null) {
            return species.getSoilMoistureMin();
        }
        return 40;   
    }

    public Integer getEffectiveLightMin() {
        if (customLightMin != null) {
            return customLightMin;
        }
        if (species != null && species.getLightMin() != null) {
            return species.getLightMin();
        }
        return 3000;   
    }


}