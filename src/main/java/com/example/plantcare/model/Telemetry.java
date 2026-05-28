package com.example.plantcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetry")
@Data
@NoArgsConstructor
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
}