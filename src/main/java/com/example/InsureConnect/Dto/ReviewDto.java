package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private PlannerDto planner;
    private UserDto user;
    private String title;
    private String content;
    private int rate;
    private Timestamp write;
    private Timestamp edit;

//    public static ReviewDto toDto(Review review) {
//        return ReviewDto.builder()
//                .id(review.getId())
//                .plannerDto(PlannerDto.toDto(review.getPlanner()))
//                .userDto(UserDto.toDto(review.getUser()))
//                .title(review.getTitle())
//                .content(review.getContent())
//                .write(review.getWrite())
//                .edit(review.getEdit())
//                .build();
//    }

//    public static List<ReviewDto> toDtoList(List<Review> reviews) {
//        return reviews.stream()
//                .map(ReviewDto::toDto)
//                .collect(Collectors.toList());
//    }

}
