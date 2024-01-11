package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import com.example.InsureConnect.Repository.PromotionRepository;
import com.example.InsureConnect.Handler.FileUploadHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionImgRepository promotionImgRepository;
    private final PlannerRepository plannerRepository;
    private final FileUploadHandler fileUploadHandler;

    public void savePromotion(String title, String content, MultipartFile[] images, CustomOAuth2User user) throws IOException {
        Planner byUserKakaoId = plannerRepository.findByUser_KakaoId(user.getId());
        String path = "classpath:/static/img/promotion";
        Promotion promotion = Promotion.builder()
                .planner(byUserKakaoId)
                .title(title)
                .content(content)
                .edit(null)
                .build();
        promotion.setWriteToCurrentTime();
        promotionRepository.save(promotion);

        List<String> imgLinks = fileUploadHandler.uploadFiles(images, path);
        List<PromotionImg> promotionImgs = new ArrayList<>();

        for (int i = 0; i < imgLinks.size(); i++) {
            PromotionImg promotionImg = PromotionImg.builder()
                    .promotion(promotion)
                    .imgLink(imgLinks.get(i))
                    .sequence(i + 1)
                    .build();
            promotionImgs.add(promotionImg);
        }
        promotionImgRepository.saveAll(promotionImgs);
    }

    public PromotionDto findByPlannerId(Long planner_id) {
        Promotion promotion = promotionRepository.findByPlannerId(planner_id);
        if(promotion == null) return null;
        else return PromotionDto.builder()
                .id(promotion.getId())
                .planner_id(planner_id)
                .title(promotion.getTitle())
                .content(promotion.getContent())
                .write(promotion.getWrite())
                .edit(promotion.getEdit())
                .build();
    }
}