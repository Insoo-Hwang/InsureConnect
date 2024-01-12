package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.*;
import com.example.InsureConnect.Entity.CustomOAuth2User;
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
    private final PromotionImgService promotionImgService;

    //Promotion 등록
    @GetMapping("/promotion/new")
    public String writePromotion(@AuthenticationPrincipal CustomOAuth2User user, Model model) {
        model.addAttribute("user", plannerService.findByUser_KakaoId(user.getId()));
        return "write_promotion";
    }

    @PostMapping("/promotion/new")
    public String registerPromotion(@RequestParam(value = "title") String title,
                                    @RequestPart(value = "content") String content,
                                    @RequestPart("images") MultipartFile[] images,
                                    @AuthenticationPrincipal CustomOAuth2User user) throws IOException {

        promotionService.savePromotion(title, content, images, user);

        return "home";
    }

    //Promotion 세부조회
    @GetMapping("/promotion/{planner_id}")
    public String detailPromotion(@PathVariable("planner_id") Long planner_id,
                                  Model model) {
        UserDto user = plannerService.findUserById(planner_id);
        PromotionDto promotion = promotionService.findByPlannerId(planner_id);
        List<PromotionImgDto> images = promotionImgService.findByPromotionId(promotion.getId());
        images.sort(Comparator.comparingInt(PromotionImgDto::getSequence));

        model.addAttribute("images", images);
        model.addAttribute("user", user);
        model.addAttribute("promotion", promotion);
        return "detail_promotion";
    }

    //Promotion 조회

    @GetMapping("/promotion")
    public String promotion(Model model) {
        List<PromotionAllDto> planners = plannerService.findAll();
        model.addAttribute("planners", planners);
        return "promotion";
    }

//    @PostMapping("/updatePromotion")
//    public String updatePromotion(@RequestParam("promotionId") Long promotionId,
//                                  @RequestParam("title") String title,
//                                  @RequestParam("content") String content,
//                                  @RequestPart("images") MultipartFile[] images,
//                                  @AuthenticationPrincipal CustomOAuth2User user) throws IOException {
//
//        // promotionId에 해당하는 Promotion을 업데이트
//        promotionService.updatePromotion(promotionId, title, content, images, user);
//
//        return "redirect:/home"; // 수정 후 홈 화면으로 리다이렉션
//    }


}