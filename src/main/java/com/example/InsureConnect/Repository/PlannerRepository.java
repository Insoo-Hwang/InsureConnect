package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Planner findByUser_KakaoId(Long user_id);
}
