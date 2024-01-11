package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PromotionApiController {

    private final PromotionService promotionService;
    private final PlannerService plannerService;

    //홍보글 존재 여부
    @GetMapping("/api/promotion/{userId}")
    public ResponseEntity<PromotionDto> checkPromotion(@PathVariable UUID userId){
        PlannerDto plannerDto = plannerService.findByUserId(userId);
        PromotionDto promotionDto = promotionService.findByPlannerId(plannerDto.getId());
        if(promotionDto == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(promotionDto);
    }

}
