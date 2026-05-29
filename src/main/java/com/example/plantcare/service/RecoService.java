package com.example.plantcare.service;

import com.example.plantcare.model.entity.Recommendation;
import com.example.plantcare.repo.RecoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.List;

@Service
public class RecoService {

    @Autowired
    private RecoRepo recommendationRepo;

    public Recommendation save(Recommendation r) {
        return recommendationRepo.save(r);
    }

    public List<Recommendation> getByPlant(Long plantId) {
        return recommendationRepo.findByPlant_IdOrderByCreatedAtDesc(plantId);
    }

    public List<Recommendation> getUnresolved(int limit) {
        return recommendationRepo.findByIsResolvedFalseOrderByCreatedAtAsc()
                .stream().limit(limit).toList();
    }

    public long countUnresolved() {
        return recommendationRepo.countByIsResolvedFalse();
    }

    public void resolve(Long id, String feedback) {
        recommendationRepo.findById(id).ifPresent(r -> {
            r.setIsResolved(true);
            r.setResolvedAt(LocalDateTime.now());
            r.setUserFeedback(feedback);
            recommendationRepo.save(r);
        });
    }
}