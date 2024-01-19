package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.*;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.PromotionImg;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.PromotionImgService;
import com.example.InsureConnect.Service.PromotionService;
import com.example.InsureConnect.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PromotionController {
    private final PlannerService plannerService;
    private final PromotionService promotionService;
    private final PromotionImgService promotionImgService;

    //Promotion 조회
    @GetMapping("/promotion")
    public String promotion(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "2") int size,
                            @RequestParam(defaultValue = "write", value = "sortField") String sortField,
                            Model model) {

        Page<PlannerDto> planners = plannerService.findAllSorted(sortField,page, size);

        model.addAttribute("planners", planners);
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
                                    @RequestPart("images") MultipartFile[] images,
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
                                  @PathVariable("promotionId") Long promotionId, Model model) {
        PromotionDto promotion = promotionService.findByPlannerId(plannerId);
        List<PromotionImgDto> images = promotionImgService.findByPromotionId(promotionId);
        model.addAttribute("promotion", promotion);
        model.addAttribute("images", images);
        return "update_promotion"; // 수정 후 홈 화면으로 리다이렉션
    }

}