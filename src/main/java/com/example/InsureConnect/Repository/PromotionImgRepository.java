package com.example.InsureConnect.Repository;

import com.example.InsureConnect.Entity.PromotionImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromotionImgRepository extends JpaRepository<PromotionImg, Long> {
    void deleteByImgLink(String imgLink);

}
