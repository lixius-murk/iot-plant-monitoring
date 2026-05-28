package com.example.plantcare.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry")
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long plantId;
    private BigDecimal temperature;
    private Integer humidityAir;
    private Integer soilMoisture;
    private Integer lightLux;
    private BigDecimal ec;
    private LocalDateTime timestamp = LocalDateTime.now();

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlantId() { return plantId; }
    public void setPlantId(Long plantId) { this.plantId = plantId; }

    public BigDecimal getTemperature() { return temperature; }
    public void setTemperature(BigDecimal temperature) { this.temperature = temperature; }

    public Integer getHumidityAir() { return humidityAir; }
    public void setHumidityAir(Integer humidityAir) { this.humidityAir = humidityAir; }

    public Integer getSoilMoisture() { return soilMoisture; }
    public void setSoilMoisture(Integer soilMoisture) { this.soilMoisture = soilMoisture; }

    public Integer getLightLux() { return lightLux; }
    public void setLightLux(Integer lightLux) { this.lightLux = lightLux; }

    public BigDecimal getEc() { return ec; }
    public void setEc(BigDecimal ec) { this.ec = ec; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}