package com.example.plantcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long plantId;
    private String type;      // WATERING, HEATING, LIGHT
    private String trigger;   // AUTO, MANUAL
    private String action;
    private LocalDateTime timestamp = LocalDateTime.now();
}