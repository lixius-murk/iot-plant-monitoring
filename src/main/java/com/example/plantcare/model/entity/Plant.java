package com.example.plantcare.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plant_species")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_species")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "temp_min", nullable = false)
    private BigDecimal tempMin;

    @Column(name = "temp_max", nullable = false)
    private BigDecimal tempMax;

    @Column(name = "hum_min", nullable = false)
    private Integer humMin;

    @Column(name = "soil_moisture_min", nullable = false)
    private Integer soilMoistureMin;

    @Column(name = "ec_target")
    private BigDecimal ecTarget;

    @Column(name = "light_min", nullable = false)
    private Integer lightMin;

    @Column(name = "recommended_pot_size_cm")
    private Integer recommendedPotSizeCm;

    @Column(name = "growth_rate_per_day")
    private BigDecimal growthRatePerDay;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getTempMin() { return tempMin; }
    public void setTempMin(BigDecimal tempMin) { this.tempMin = tempMin; }
    public BigDecimal getTempMax() { return tempMax; }
    public void setTempMax(BigDecimal tempMax) { this.tempMax = tempMax; }
    public Integer getHumMin() { return humMin; }
    public void setHumMin(Integer humMin) { this.humMin = humMin; }
    public Integer getSoilMoistureMin() { return soilMoistureMin; }
    public void setSoilMoistureMin(Integer soilMoistureMin) { this.soilMoistureMin = soilMoistureMin; }
    public BigDecimal getEcTarget() { return ecTarget; }
    public void setEcTarget(BigDecimal ecTarget) { this.ecTarget = ecTarget; }
    public Integer getLightMin() { return lightMin; }
    public void setLightMin(Integer lightMin) { this.lightMin = lightMin; }
    public Integer getRecommendedPotSizeCm() { return recommendedPotSizeCm; }
    public void setRecommendedPotSizeCm(Integer recommendedPotSizeCm) { this.recommendedPotSizeCm = recommendedPotSizeCm; }
    public BigDecimal getGrowthRatePerDay() { return growthRatePerDay; }
    public void setGrowthRatePerDay(BigDecimal growthRatePerDay) { this.growthRatePerDay = growthRatePerDay; }
    public String getColorCode() { return colorCode; }
    public void setColorCode(String colorCode) { this.colorCode = colorCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}