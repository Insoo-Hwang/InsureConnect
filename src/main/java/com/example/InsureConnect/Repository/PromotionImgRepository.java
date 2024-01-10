package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.PromotionImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface PromotionImgRepository extends JpaRepository<PromotionImg, Long> {

    List<PromotionImg> findByPromotionId(Long promotionId);
}
