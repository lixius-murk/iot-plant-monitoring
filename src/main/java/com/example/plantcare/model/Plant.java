package com.example.plantcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plants")
@Data
@NoArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;

    // Пороговые значения
    private BigDecimal tempMin;
    private BigDecimal tempMax;
    private Integer humidityMin;
    private Integer soilMoistureMin;
    private Integer lightMin;

    // Текущее состояние
    private Integer state = 0;  // 0=ok, 1=attention
    private String health = "HEALTHY";
    private BigDecimal height;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastWateredAt;

    // Пользовательские настройки (переопределяют пороги)
    private BigDecimal customTempMin;
    private Integer customSoilMoistureMin;
    private Integer customLightMin;

    // Метод для получения эффективного порога
    public BigDecimal getEffectiveTempMin() {
        return customTempMin != null ? customTempMin : tempMin;
    }

    public Integer getEffectiveSoilMoistureMin() {
        return customSoilMoistureMin != null ? customSoilMoistureMin : soilMoistureMin;
    }

    public Integer getEffectiveLightMin() {
        return customLightMin != null ? customLightMin : lightMin;
    }
}