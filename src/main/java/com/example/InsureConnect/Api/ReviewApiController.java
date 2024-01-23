package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping("/api/review")
    public ResponseEntity<Slice<ReviewDto>> review(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "", value = "search") String search,
            @RequestParam(defaultValue = "", value = "criteria") String criteria) {

        Pageable pageable = PageRequest.of(page, size);
        Slice<ReviewDto> reviews = reviewService.findAllReivews(pageable,search,criteria);

        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/api/review/{reviewId}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable Long reviewId) {
        ReviewDto dto = reviewService.delete(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
