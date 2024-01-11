package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.Review;
import com.example.InsureConnect.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long id);
    List<Review> findByUser(User user);
}
