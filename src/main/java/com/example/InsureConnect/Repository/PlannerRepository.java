package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Planner findByUser_KakaoId(Long kakao_id);

    Planner findByUser(User user);

    @Query("SELECT p.user FROM Planner p WHERE p.id = :plannerId")
    User findUserByPlannerId(@Param("plannerId") Long plannerId);

    @Query(value = "SELECT * FROM Planner WHERE status = 'enroll'", nativeQuery = true)
    List<Planner> findByStatusEnroll();

    @Query(value = "SELECT * FROM Planner WHERE status = 'permit'", nativeQuery = true)
    List<Planner> findAllPermitPlanner();

    @Query("SELECT p FROM Planner p JOIN FETCH p.promotion pr " +
            "WHERE p.status = 'permit' ORDER BY pr.write DESC")
    Page<Planner> findAllPermitPlanner(Pageable pageable);

    @Query("SELECT p FROM Planner p LEFT JOIN p.review r GROUP BY p ORDER BY AVG(r.rate) DESC")
    Page<Planner> findAllPermitPlannerOrderRating(Pageable pageable);

    @Query("select p from Planner p order by size(p.review) DESC ")
    Page<Planner> findAllOrderByRateCount(Pageable pageable);

}
