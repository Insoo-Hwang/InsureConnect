package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long>, JpaSpecificationExecutor<Planner> {
    Planner findByUser_KakaoId(Long kakao_id);

    Planner findByUser(User user);

    @Query("SELECT p.user FROM Planner p WHERE p.id = :plannerId")
    User findUserByPlannerId(@Param("plannerId") Long plannerId);

    @Query(value = "SELECT * FROM Planner WHERE status = 'enroll'", nativeQuery = true)
    List<Planner> findByStatusEnroll();

    @Query(value = "SELECT * FROM Planner WHERE status = 'permit'", nativeQuery = true)
    List<Planner> findAllPermitPlanner();
}
