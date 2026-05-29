package com.example.plantcare.repo;

import com.example.plantcare.model.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecoRepo extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByPlant_IdOrderByCreatedAtDesc(Long plantId);
    List<Recommendation> findByIsResolvedFalseOrderByCreatedAtAsc();
    long countByIsResolvedFalse();
}