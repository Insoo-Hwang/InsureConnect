package com.example.InsureConnect.Api;

import com.example.InsureConnect.Entity.Promotion;
import com.example.InsureConnect.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
public class PromotionApiController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/api/promotion")
    public ResponseEntity<?> create(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) List<MultipartFile> files
            ) throws Exception{
        Promotion promotion = promotionService.create(Promotion.builder()
                .title(title)
                .content(content)
                .build(), files);
        URI uriLocation = new URI("/api/promotion" + promotion.getId());
        return ResponseEntity.created(uriLocation).body("{}");
    }
}
