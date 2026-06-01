//package com.example.plantcare.controller;
//
//import com.example.plantcare.model.Dto;
//import com.example.plantcare.service.PlantService;
//import com.example.plantcare.service.RecoService;
//import com.example.plantcare.service.TelemetryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/plants")
//public class PlantController {
//
//    @Autowired private PlantService plantService;
//    @Autowired private TelemetryService telemetryService;
//    @Autowired private RecoService recommendationService;
//
//    @GetMapping
//    public List<Dto.PlantResponse> getAllPlants() {
//        return plantService.getAllActive().stream().map(Dto.PlantResponse::from).toList();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Dto.PlantResponse> getPlant(@PathVariable Long id) {
//        return plantService.findById(id)
//                .map(Dto.PlantResponse::from)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public Dto.PlantResponse createPlant(@RequestBody Dto.PlantRequest req) {
//        return Dto.PlantResponse.from(plantService.create(req));
//    }
//
//    @PutMapping("/{id}/settings")
//    public ResponseEntity<Dto.PlantResponse> updateSettings(
//            @PathVariable Long id, @RequestBody Dto.Settings settings) {
//        return ResponseEntity.ok(Dto.PlantResponse.from(plantService.updateSettings(id, settings)));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
//        plantService.delete(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{id}/telemetry/latest")
//    public ResponseEntity<Dto.TelemetryData> getLatestTelemetry(@PathVariable Long id) {
//        return telemetryService.getLatestByPlant(id)
//                .map(Dto.TelemetryData::from)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/{id}/telemetry/history")
//    public List<Dto.TelemetryData> getTelemetryHistory(
//            @PathVariable Long id,
//            @RequestParam(defaultValue = "24") int hours) {
//        return telemetryService.getHistory(id, hours)
//                .stream().map(Dto.TelemetryData::from).toList();
//    }
//
//    @GetMapping("/{id}/recommendations")
//    public List<Dto.RecommendationData> getRecommendations(@PathVariable Long id) {
//        return recommendationService.getByPlant(id)
//                .stream().map(Dto.RecommendationData::from).toList();
//    }
//
//    @PostMapping("/{id}/recommendations/{recId}/resolve")
//    public ResponseEntity<Void> resolveRecommendation(
//            @PathVariable Long id,
//            @PathVariable Long recId,
//            @RequestBody(required = false) String feedback) {
//        recommendationService.resolve(recId, feedback);
//        return ResponseEntity.ok().build();
//    }
//}