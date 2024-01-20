package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    //promtion 조회, 정렬
    @GetMapping("/api/promotion")
    public ResponseEntity<Slice<PlannerDto>> promotion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "write", value = "sortField") String sortField,
            @RequestParam(defaultValue = "", value = "search") String search,
            @RequestParam(defaultValue = "", value = "criteria") String criteria) {


        Pageable pageable = PageRequest.of(page, size);
        Slice<PlannerDto> planners = plannerService.findAllPermitPlanner(pageable, sortField, search, criteria);

        return ResponseEntity.ok(planners);
    }

    //promotion 수정
    @PatchMapping("/api/promotion/{plannerId}/{promotionId}")
    public ResponseEntity<PromotionDto> updatePromotion(@PathVariable("plannerId") Long plannerId,
                                                        @PathVariable("promotionId") Long promotionId,
                                                        @RequestPart("images") MultipartFile[] images) throws IOException {
        PromotionDto promotion = promotionService.findByPlannerId(plannerId);

        if (promotion == null || !promotion.getId().equals(promotionId) || !promotion.getPlannerId().equals(plannerId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(promotionService.updatePromotion(promotion, images));
        }
    }
}
