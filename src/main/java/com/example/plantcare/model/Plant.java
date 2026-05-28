package com.example.plantcare.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private BigDecimal tempMin;
    private BigDecimal tempMax;
    private Integer humidityMin;
    private Integer soilMoistureMin;
    private Integer lightMin;
    private Integer state = 0;
    private String health = "HEALTHY";
    private BigDecimal height;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastWateredAt;
    private BigDecimal customTempMin;
    private Integer customSoilMoistureMin;
    private Integer customLightMin;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public BigDecimal getTempMin() { return tempMin; }
    public void setTempMin(BigDecimal tempMin) { this.tempMin = tempMin; }

    public BigDecimal getTempMax() { return tempMax; }
    public void setTempMax(BigDecimal tempMax) { this.tempMax = tempMax; }

    public Integer getHumidityMin() { return humidityMin; }
    public void setHumidityMin(Integer humidityMin) { this.humidityMin = humidityMin; }

    public Integer getSoilMoistureMin() { return soilMoistureMin; }
    public void setSoilMoistureMin(Integer soilMoistureMin) { this.soilMoistureMin = soilMoistureMin; }

    public Integer getLightMin() { return lightMin; }
    public void setLightMin(Integer lightMin) { this.lightMin = lightMin; }

    public Integer getState() { return state; }
    public void setState(Integer state) { this.state = state; }

    public String getHealth() { return health; }
    public void setHealth(String health) { this.health = health; }

    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastWateredAt() { return lastWateredAt; }
    public void setLastWateredAt(LocalDateTime lastWateredAt) { this.lastWateredAt = lastWateredAt; }

    public BigDecimal getCustomTempMin() { return customTempMin; }
    public void setCustomTempMin(BigDecimal customTempMin) { this.customTempMin = customTempMin; }

    public Integer getCustomSoilMoistureMin() { return customSoilMoistureMin; }
    public void setCustomSoilMoistureMin(Integer customSoilMoistureMin) { this.customSoilMoistureMin = customSoilMoistureMin; }

    public Integer getCustomLightMin() { return customLightMin; }
    public void setCustomLightMin(Integer customLightMin) { this.customLightMin = customLightMin; }

    public Integer getEffectiveSoilMoistureMin() {
        return customSoilMoistureMin != null ? customSoilMoistureMin : soilMoistureMin;
    }

    public BigDecimal getEffectiveTempMin() {
        return customTempMin != null ? customTempMin : tempMin;
    }

    public Integer getEffectiveLightMin() {
        return customLightMin != null ? customLightMin : lightMin;
    }
}