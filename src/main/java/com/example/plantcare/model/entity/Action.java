package com.example.plantcare.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "command_topic")
    private String commandTopic;

    @Column(name = "default_payload", columnDefinition = "JSONB")
    private String defaultPayload;

    @Column(name = "requires_confirmation")
    private Boolean requiresConfirmation = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCommandTopic() { return commandTopic; }
    public void setCommandTopic(String commandTopic) { this.commandTopic = commandTopic; }

    public String getDefaultPayload() { return defaultPayload; }
    public void setDefaultPayload(String defaultPayload) { this.defaultPayload = defaultPayload; }

    public Boolean getRequiresConfirmation() { return requiresConfirmation; }
    public void setRequiresConfirmation(Boolean requiresConfirmation) { this.requiresConfirmation = requiresConfirmation; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}