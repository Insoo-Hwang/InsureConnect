package com.example.InsureConnect.Dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlannerDto {

    private Long id;

    private UserDto user;

    private String profile;

    private String company;

    private String certificate;

    private String status;

    private PromotionDto promotion;

    private List<ReviewDto> review;

    private MultipartFile[] f = new MultipartFile[2];

//    public static PlannerDto toDto(Planner planner) {
//        return PlannerDto.builder()
//                .id(planner.getId())
//                .userDto(UserDto.toDto(planner.getUser()))
//                .profile(planner.getProfile())
//                .company(planner.getCompany())
//                .certificate(planner.getCertificate())
//                .status(planner.getStatus())
//                .promotionDto(PromotionDto.toDto(planner.getPromotion()))
//                .reviewDto(ReviewDto.toDtoList(planner.getReviews())).build();
//    }

    public double getAverageRating() {
        if (review != null && !review.isEmpty()) {
            double sum = review.stream().mapToInt(ReviewDto::getRate).sum();
            double average = sum / review.size();

            // 평균을 소수점 한 자리까지 반올림하여 문자열로 변환
            return Double.parseDouble(String.format("%.1f", average));
        }
        return 0.0; // 평점이 없는 경우 0으로 반환하거나 다른 값을 지정할 수 있습니다.
    }

}
