package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.PromotionDto;
import com.example.InsureConnect.Dto.PromotionImgDto;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.PromotionImgService;
import com.example.InsureConnect.Service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PromotionController {
    private final PlannerService plannerService;
    private final PromotionService promotionService;

    //Promotion 조회
    @GetMapping("/promotion")
    public String promotion() {
        return "promotion";
    }
    //Promotion 등록
    @GetMapping("/promotion/new")
    public String writePromotion() {
        return "write_promotion";
    }


    @PostMapping("/promotion/new")
    public String registerPromotion(@RequestParam(value = "title") String title,
                                    @RequestPart(value = "content") String content,
                                    @RequestPart("images") List<MultipartFile> images,
                                    @AuthenticationPrincipal CustomOAuth2User user) throws IOException {

        promotionService.savePromotion(title, content, images, user);

        return "home";
    }

    //Promotion 세부조회
    @GetMapping("/promotion/{planner_id}")
    public String detailPromotion(@PathVariable("planner_id") Long plannerId,
                                  Model model) {
        PlannerDto planner = plannerService.findById(plannerId);
        PromotionDto promotion = promotionService.findByPlannerId(plannerId);
        promotion.getPromotionImg().sort(Comparator.comparingInt(PromotionImgDto::getSequence));

        model.addAttribute("planner", planner);
        model.addAttribute("promotion", promotion);
        return "detail_promotion";
    }

    @GetMapping("/promotion/update/{plannerId}/{promotionId}")
    public String updatePromotion(@PathVariable("plannerId") Long plannerId,
                                  @PathVariable("promotionId") Long promotionId,
                                  Model model) {
        PromotionDto promotion = promotionService.findByPlannerId(plannerId);

        model.addAttribute("plannerId", plannerId);
        model.addAttribute("promotionId", promotionId);
        model.addAttribute("promotion", promotion);
        return "update_promotion";
    }

}