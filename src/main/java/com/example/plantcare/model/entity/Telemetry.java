package com.example.plantcare.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry")
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telemetry")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plant")
    private PlantInstance plant;

    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;

    @Column(name = "temperature")
    private BigDecimal temperature;

    @Column(name = "humidity_air")
    private Integer humidityAir;

    @Column(name = "soil_moisture")
    private Integer soilMoisture;

    @Column(name = "light_lux")
    private Integer lightLux;

    @Column(name = "ec")
    private BigDecimal ec;

    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();


    @Column(name = "is_valid")
    private Boolean isValid = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PlantInstance getPlant() { return plant; }
    public void setPlant(PlantInstance plant) { this.plant = plant; }

    public Sensor getSensor() { return sensor; }
    public void setSensor(Sensor sensor) { this.sensor = sensor; }

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

    public Boolean getIsValid() { return isValid; }
    public void setIsValid(Boolean isValid) { this.isValid = isValid; }
}