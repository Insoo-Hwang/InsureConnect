package com.example.InsureConnect.Api;

import com.example.InsureConnect.Dto.ReviewDto;
import com.example.InsureConnect.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @DeleteMapping("/api/review/{reviewId}")
    public ResponseEntity<ReviewDto> deleteReview(@PathVariable Long reviewId){
        ReviewDto dto = reviewService.delete(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
