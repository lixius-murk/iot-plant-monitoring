package com.example.plantcare.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plant")
    private PlantInstance plant;

    @ManyToOne
    @JoinColumn(name = "id_telemetry")
    private Telemetry telemetry;

    @ManyToOne
    @JoinColumn(name = "id_action")
    private Action action;

    @Column(name = "trigger_type")
    private String triggerType = "AUTO";

    @Column(name = "priority")
    private Integer priority = 1;

    @Column(name = "event_state")
    private Integer eventState = 0;

    @Column(name = "command_sent")
    private Boolean commandSent = false;

    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PlantInstance getPlant() { return plant; }
    public void setPlant(PlantInstance plant) { this.plant = plant; }

    public Telemetry getTelemetry() { return telemetry; }
    public void setTelemetry(Telemetry telemetry) { this.telemetry = telemetry; }

    public Action getAction() { return action; }
    public void setAction(Action action) { this.action = action; }

    public String getTriggerType() { return triggerType; }
    public void setTriggerType(String triggerType) { this.triggerType = triggerType; }

    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }


    public Integer getEventState() { return eventState; }
    public void setEventState(Integer eventState) { this.eventState = eventState; }

    public Boolean getCommandSent() { return commandSent; }
    public void setCommandSent(Boolean commandSent) { this.commandSent = commandSent; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}