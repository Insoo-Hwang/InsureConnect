package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {
    List<ReviewImg> findByReviewId(Long reviewID);
}
