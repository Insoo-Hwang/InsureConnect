package com.example.InsureConnect.Service;


import com.example.InsureConnect.Dto.PromotionImgDto;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PromotionImgService {
    private final PromotionImgRepository promotionImgRepository;
    public List<PromotionImgDto> findByPromotionId(Long promotionId) {
        List<PromotionImg> promotionImgs = promotionImgRepository.findByPromotionId(promotionId);
        List<PromotionImgDto> promotionImgDtos = new ArrayList<>();

        for (PromotionImg promotionImg : promotionImgs) {
            PromotionImgDto promotionImgDto = PromotionImgDto.builder()
                    .id(promotionImg.getId())
                    .imgLink(promotionImg.getImgLink())
                    .sequence(promotionImg.getSequence())
                    .build();

            promotionImgDtos.add(promotionImgDto);
        }

        return promotionImgDtos;
    }

}
