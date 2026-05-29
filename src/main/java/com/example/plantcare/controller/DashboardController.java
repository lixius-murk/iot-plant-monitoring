package com.example.plantcare.controller;

import com.example.plantcare.service.EventService;
import com.example.plantcare.service.PlantService;
import com.example.plantcare.service.RecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired private PlantService plantService;
    @Autowired private EventService eventService;
    @Autowired private RecoService recommendationService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalPlants", plantService.countActive());
        summary.put("healthyPlants", plantService.countByHealthStatus("HEALTHY"));
        summary.put("plantsNeedingAttention", plantService.countByState(1));
        summary.put("pendingRecommendations", recommendationService.countUnresolved());
        summary.put("recentEvents", eventService.getRecent());
        summary.put("recommendations", recommendationService.getUnresolved(5));
        return ResponseEntity.ok(summary);
    }
}