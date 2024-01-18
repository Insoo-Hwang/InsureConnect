package com.example.InsureConnect.Controller;

import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Entity.CustomOAuth2User;
import com.example.InsureConnect.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/review")
    public String review(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "2") int size,
                         Model model) {
        Page<ReviewDto> reviewsPage = reviewService.findAll(PageRequest.of(page - 1, size));

        model.addAttribute("reviewsPage", reviewsPage);
        return "/review";
    }

    @GetMapping("/review/new")
    public String writeReview() {
        return "/write_review";
    }

    @PostMapping("/review/new")
    public String saveReview(@ModelAttribute ReviewDto reviewDto,
                             @AuthenticationPrincipal CustomOAuth2User user,
                             @RequestParam("planner_id") Long plannerId,
                             @RequestPart("images") MultipartFile[] images) throws IOException {
        reviewService.saveReview(reviewDto,images,user,plannerId);

        return "/home";
    }

    @GetMapping("/review/detail/{review_id}")
    public String detailReview(@PathVariable("review_id") Long reviewId, Model model) {
        ReviewDto review = reviewService.findById(reviewId);

        model.addAttribute("review", review);
        return "review_detail";
    }

    @GetMapping("/review/{plannerId}")
    public String reviewByPlanner(@PathVariable("plannerId")Long plannerId, Model model) {
        List<ReviewDto> reviewList = reviewService.findByPlannerId(plannerId);
        model.addAttribute("reviewList",reviewList);
        return "review";
    }
}
