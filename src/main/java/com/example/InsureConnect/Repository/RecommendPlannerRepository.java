package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.RecommendPlanner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendPlannerRepository  extends JpaRepository<RecommendPlanner, Long> {

    RecommendPlanner findFirstByOrderByTimeDesc();
}
