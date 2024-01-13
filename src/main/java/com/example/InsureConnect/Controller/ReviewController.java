package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.PlannerDto;
import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.PlannerService;
import com.example.InsureConnect.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final PlannerService plannerService;

    @GetMapping("/review/new")
    public String writeReview() {
        return "/write_review";
    }

    @PostMapping("/review/new")
    public String saveReview(@ModelAttribute ReviewDto reviewDto,
                             @AuthenticationPrincipal CustomOAuth2User user,
                             @RequestParam("planner_id") Long planner_id,
                             @RequestPart("images") MultipartFile[] images) throws IOException {
        reviewService.saveReview(reviewDto,images,user,planner_id);

        return "/home";
    }

}
