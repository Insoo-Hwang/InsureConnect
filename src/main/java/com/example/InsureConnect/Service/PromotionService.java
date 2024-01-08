package com.example.InsureConnect.Service;

import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import com.example.InsureConnect.Repository.PromotionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionImgRepository promotionImgRepository;

    private final String path = "C:\\Users\\iamin\\Downloads\\InsureConnect\\InsureConnect\\src\\main\\resources\\static\\img";

    @Transactional
    public PromotionDto create(PromotionDto dto, MultipartFile[] images){
        Promotion promotion = Promotion.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        List<PromotionImg> savedImages = new ArrayList<>();
        if(images != null) {
            for (MultipartFile image : images) {
                String savedFileName = saveImages(image);
                PromotionImg promotionImg = PromotionImg.builder()
                        .imgLink(savedFileName)
                        .build();
                promotion.addPromotionImage(promotionImg);
                savedImages.add(promotionImg);
            }
        }
        Promotion created = promotionRepository.save(promotion);
        for(PromotionImg img : savedImages){
            img.setPromotion(created);
            promotionImgRepository.save(img);
        }
        return PromotionDto.builder()
                .id(created.getId())
                .title(created.getTitle())
                .content(created.getContent())
                //.plannerId(created.getPlanner().getId())
                .write(created.getWrite())
                .edit(created.getEdit())
                .build();
    }

    private String saveImages(MultipartFile image){
        try {
            String originalName = image.getOriginalFilename();
            String savedName = System.currentTimeMillis()+"_"+originalName;

            File file = new File(this.path, savedName);
            image.transferTo(file);
            return savedName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
