package com.example.plantcare.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensor")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sensor_type")
    private SensorType sensorType;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlantSensor> plantSensors = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "pin_number")
    private Integer pinNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public List<PlantInstance> getPlants() {
        return plantSensors.stream()
                .filter(PlantSensor::getIsActive)
                .map(PlantSensor::getPlant)
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public SensorType getSensorType() { return sensorType; }
    public void setSensorType(SensorType sensorType) { this.sensorType = sensorType; }
    public List<PlantSensor> getPlantSensors() { return plantSensors; }
    public void setPlantSensors(List<PlantSensor> plantSensors) { this.plantSensors = plantSensors; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getPinNumber() { return pinNumber; }
    public void setPinNumber(Integer pinNumber) { this.pinNumber = pinNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}