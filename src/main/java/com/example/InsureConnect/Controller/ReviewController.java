package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Config.OAuth.CustomOAuth2User;
import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final PlannerService plannerService;

    @GetMapping("/review")
    public String review() {
        return "review";
    }

    @GetMapping("/review/new")
    public String writeReview() {
        return "/write_review";
    }

    @GetMapping("/review/detail/{review_id}")
    public String detailReview(@PathVariable("review_id") Long reviewId, Model model) {
        ReviewDto review = reviewService.findById(reviewId);
        PlannerDto planner = plannerService.findById(review.getPlannerId());

        model.addAttribute("planner", planner);
        model.addAttribute("review", review);
        return "review_detail";
    }
    @PostMapping("/review/new")
    public String saveReview(@ModelAttribute ReviewDto reviewDto,
                             @AuthenticationPrincipal CustomOAuth2User user,
                             @RequestParam("planner_id") Long plannerId,
                             @RequestPart("images") List<MultipartFile> images) throws IOException {
        reviewService.saveReview(reviewDto,images,user,plannerId);

        return "redirect:/";
    }
}
