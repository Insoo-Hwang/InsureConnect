package com.example.InsureConnect.Service;

import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Repository.PromotionImgRepository;
import com.example.InsureConnect.Repository.PromotionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private FileHandler fileHandler;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionImgRepository promotionImgRepository;

    @Transactional
    public Promotion create(Promotion promotion, List<MultipartFile> files) throws Exception{
        if(files != null){
            List<PromotionImg> list = fileHandler.parseFileInfo(files);

            for(PromotionImg promotionImg : list){
                promotion.addPromotionImage(promotionImg);
            }
            promotion = promotionRepository.save(promotion);
            for(PromotionImg promotionImg : list){
                promotionImg.setPromotion(promotion);
                promotionImgRepository.save(promotionImg);
            }
        }
        return promotion;
    }
}