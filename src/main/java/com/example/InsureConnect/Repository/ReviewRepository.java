package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Review;
import com.example.InsureConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> , JpaSpecificationExecutor<Review> {

    Optional<Review> findById(Long id);
    List<Review> findByUser(User user);
    List<Review> findByPlannerId(Long plannerId);
}
