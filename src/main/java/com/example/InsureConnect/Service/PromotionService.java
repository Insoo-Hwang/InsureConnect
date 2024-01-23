package com.example.InsureConnect.Service;

import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Repository.PlannerRepository;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import com.example.InsureConnect.Repository.PromotionRepository;
import com.example.InsureConnect.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionImgRepository promotionImgRepository;
    private final PlannerRepository plannerRepository;
    private final FileHandler fileHandler;
    private final ModelMapper modelMapper;

    //PromotionDto 조회 used PlannerId
    public PromotionDto findByPlannerId(Long plannerId) {
        Promotion promotion = promotionRepository.findByPlannerId(plannerId);
        if (promotion == null) return null;
        return modelMapper.map(promotion, PromotionDto.class);
    }

    @Transactional //Promotion 저장
    public void savePromotion(String title, String content, List<MultipartFile> images, CustomOAuth2User user) throws IOException {
        Planner planner = getPlannerByUserKakaoId(user);
        Promotion promotion = createPromotion(title, content, planner);
        saveImages(promotion,images);
    }

    @Transactional //Promotion 수정
    public ResponseEntity<PromotionDto> updatePromotion(PromotionDto promotionDto,
                                                        String title, String content,
                                                        List<MultipartFile> images,
                                                        List<String> existingImages) throws IOException {

        Promotion promotion = getPromotionById(promotionDto.getId());
        updatePromotionDetails(promotion, title, content, promotionDto);
        handlePromotionImages(promotion, images, existingImages);

        return ResponseEntity.ok(modelMapper.map(promotion, PromotionDto.class));
    }

    //Promotion 조회 used promotionId
    private Promotion getPromotionById(Long promotionId) {
        return promotionRepository.findById(promotionId).orElseThrow(IllegalArgumentException::new);
    }

    //planner 조회 used User.kakaoId
    private Planner getPlannerByUserKakaoId(CustomOAuth2User user) {
        return plannerRepository.findByUser_KakaoId(user.getId());
    }

    //Promotion db 저장
    private Promotion createPromotion(String title, String content, Planner planner) {
        Promotion promotion = Promotion.builder()
                .planner(planner)
                .title(title)
                .content(content)
                .build();
        promotion.setWriteToCurrentTime();

        return promotionRepository.save(promotion);
    }

    //promotion db 수정
    private void updatePromotionDetails(Promotion promotion, String title, String content,
                                        PromotionDto promotionDto) {
        promotion = Promotion.builder()
                .id(promotion.getId())
                .planner(promotion.getPlanner())
                .title(title)
                .content(content)
                .write(promotion.getWrite())
                .edit(promotionDto.getEdit())
                .promotionImg(promotion.getPromotionImg())
                .build();
        promotionRepository.save(promotion);
    }

    //Promotion images 저장
    private void saveImages(Promotion promotion, List<MultipartFile> images) throws IOException {
        if (images != null && !images.isEmpty()) {
            String path = "classpath:/static/img/promotion";
            List<String> imageList = fileHandler.uploadFiles(images, path);
            savePromotionImagesToDatabase(promotion, imageList);
        }
    }

    //Promotion images db 저장
    private void savePromotionImagesToDatabase(Promotion promotion, List<String> imageList) {

        List<PromotionImg> promotionImgs = new ArrayList<>();

        for (int i = 0; i < imageList.size(); i++) {
            PromotionImg promotionImg = PromotionImg.builder()
                    .promotion(promotion)
                    .imgLink(imageList.get(i))
                    .sequence(getNextSequence(promotion.getPromotionImg()) + i + 1)
                    .build();
            promotionImgs.add(promotionImg);
        }
        promotionImgRepository.saveAll(promotionImgs);
    }

    //Promotion images 저장,삭제
    private void handlePromotionImages(Promotion promotion, List<MultipartFile> images, List<String> existingImages) throws IOException {
        deleteExistingImages(promotion, existingImages);
        saveImages(promotion, images);
    }

    //존재하는 Promotion images 비교 삭제 및 저장
    private void deleteExistingImages(Promotion promotion, List<String> existingImages) throws IOException {
        List<PromotionImg> promotionImgs = promotion.getPromotionImg();

        List<String> promotionImages = new ArrayList<>();

        for (PromotionImg promotionImg : promotionImgs) {
            promotionImages.add(promotionImg.getImgLink());
        }

        if (existingImages != null && !existingImages.isEmpty()) {
            Iterator<String> iterator = promotionImages.iterator();

            while (iterator.hasNext()) {
                String promotionImage = iterator.next();
                boolean shouldRemove = true;

                for (String existingImage : existingImages) {
                    if (existingImage.equals(promotionImage)) {
                        shouldRemove = false;
                        break;
                    }
                }

                if (shouldRemove) {
                    promotionImgRepository.deleteByImgLink(promotionImage);
                    fileHandler.deleteFile(promotionImage);
                    iterator.remove();
                }
            }
        }
    }

    // 다음 시퀀스 값을 가져오는 메서드
    private int getNextSequence(List<PromotionImg> promotionImgs) {
        if (promotionImgs.isEmpty()) {
            return 0;
        }
        return promotionImgs.stream()
                .mapToInt(PromotionImg::getSequence)
                .max()
                .orElse(0);
    }

}