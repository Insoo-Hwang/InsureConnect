package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PromotionApiController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping(value = "/api/promotion")
    public ResponseEntity<PromotionDto> create(@RequestBody PromotionDto dto, @RequestParam(value = "images", required = false) MultipartFile[] images){
        PromotionDto created = promotionService.create(dto, images);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }
}
