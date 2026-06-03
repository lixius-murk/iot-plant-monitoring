package com.example.plantcare.service;

import com.example.plantcare.model.Dto;
import com.example.plantcare.model.entity.Event;
import com.example.plantcare.model.entity.PlantInstance;
import com.example.plantcare.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    public Event save(Event e) {
        return eventRepo.save(e);
    }

    public List<Event> getByPlant(Long plantId) {
        return eventRepo.findByPlant_IdOrderByTimestampDesc(plantId);
    }

    public List<Event> getRecent() {
        return eventRepo.findRecent();
    }

    public Event createAutoEvent(PlantInstance plant, String type, String action) {
        Event e = new Event();
        e.setPlant(plant);
        e.setEventType(type);
        e.setTriggerType("AUTO");
        e.setActionTaken(action);
        e.setEventState(2);
        return eventRepo.save(e);
    }
    public List<Dto.EventResponse> getRecentEventsWithCorrectFormat() {
        return eventRepo.findRecent().stream()
                .map(e -> {
                    Dto.EventResponse dto = new Dto.EventResponse();
                    dto.setEventType(e.getEventType());
                    dto.setActionTaken(e.getActionTaken());
                    dto.setTimestamp(e.getTimestamp() != null ? e.getTimestamp().toString() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}