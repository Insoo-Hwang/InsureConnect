package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Dto.PromotionImgDto;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import com.example.InsureConnect.Repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionImgRepository promotionImgRepository;

    @Autowired
    private PlannerRepository plannerRepository;

    public Long create(Long plannerId, PromotionDto promotionDto){
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException());
        Promotion promotion = Promotion.toPromotion(promotionDto, planner);
        List<PromotionImgDto> promotionImgDtos = promotionDto.getPromotionImgDtos();
        if (promotionImgDtos != null) {
            for (PromotionImgDto imgDto : promotionImgDtos) {
                PromotionImg promotionImg = PromotionImg.toPromotionImg(imgDto);
                promotion.addPromotionImage(promotionImg);
                promotionImgRepository.save(promotionImg);
            }
        }
        Promotion saved = promotionRepository.save(promotion);
        return saved.getId();
    }
}
