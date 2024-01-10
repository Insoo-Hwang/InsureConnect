package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Promotion findByPlannerId(Long planner_id);
}
