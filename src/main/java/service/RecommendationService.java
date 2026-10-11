package service;

import model.entity.PlantInstance;
import model.entity.Recommendation;
import model.entity.RecommendationMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.RecommendationMsgRepository;
import repo.RecommendationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationService {

    @Autowired
    private RecommendationMsgRepository recommendationMsgRepository;

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private WebSocketService webSocketService;

    public long countUnresolved() {
        return recommendationRepository.countByResolvedFalse();
    }

    public boolean existsUnresolved(Long plantId, Long msgId) {
        return recommendationRepository.existsByPlant_IdAndMessage_IdAndResolvedFalse(plantId, msgId);
    }
    public void createPotSizeIfAbsent(PlantInstance plant, String text) {
        if (recommendationRepository.existsByPlant_IdAndResolvedFalse(plant.getId())) return;
        RecommendationMsg msg = recommendationMsgRepository.save(new RecommendationMsg(text));
        save(new Recommendation(plant, msg, "INFO"));
    }
    public boolean wasRecentlyResolved(Long plantId, Long msgId, int cooldownMinutes) {
        return recommendationRepository.existsRecentlyResolved(plantId, msgId, LocalDateTime.now().minusMinutes(cooldownMinutes));
    }
    public List<Object[]> getUnresolved() {
        return recommendationRepository.getUnresolved();
    }

    public List<Recommendation> getUnresolvedList() {
        return recommendationRepository.findByResolvedFalseOrderByCreatedAtAsc();
    }

    public void resolve(Long recId) {
        Recommendation rec = recommendationRepository.findById(recId).orElse(null);
        recommendationRepository.resolve(recId);

        if (rec != null) {
            webSocketService.sendRecommendation(rec.getPlant(), rec);
            System.out.println("sent recommendation resolve");
        }

    }

    public List<Recommendation> getByPlant(Long plantId) {
        return recommendationRepository.getByPlantId(plantId);
    }

    public List<Recommendation> getUnresByPlant(Long plantId) {
        return recommendationRepository.getUnresByPlantId(plantId);
    }

    public Recommendation save(Recommendation recommendation) {
        recommendation.setCreatedAt(LocalDateTime.now());
        recommendation.setResolved(false);
        return recommendationRepository.save(recommendation);
    }
    public void createIfAbsent(PlantInstance plant, Long msgId, String severity) {
        if (recommendationRepository.existsByPlant_IdAndMessage_IdAndResolvedFalse(plant.getId(), msgId)) return;
        RecommendationMsg msg = recommendationMsgRepository.findById(msgId).orElse(null);
        if (msg == null) return;
        save(new Recommendation(plant, msg, severity));
    }
    public List<Recommendation> saveAll(List<Recommendation> recommendations) {
        for (Recommendation rec : recommendations) {
            rec.setCreatedAt(LocalDateTime.now());
            rec.setResolved(false);
        }
        return recommendationRepository.saveAll(recommendations);
    }
}