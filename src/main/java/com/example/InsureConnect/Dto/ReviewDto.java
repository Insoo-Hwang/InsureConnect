package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Review;
import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDto {
    private Long id;
    private Long plannerId;
    private UUID userId;
    private String title;
    private String content;
    private int rate;
    private Timestamp write;
    private Timestamp edit;

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(review.getId(), review.getPlanner().getId(), review.getUser().getId(), review.getTitle(), review.getContent(), review.getRate(), review.getWrite(), review.getEdit());
    }

    public static List<ReviewDto> toDtoList(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewDto::toDto)
                .collect(Collectors.toList());
    }

}
