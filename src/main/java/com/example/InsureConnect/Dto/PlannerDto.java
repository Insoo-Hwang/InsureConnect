package com.example.InsureConnect.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlannerDto {

    private Long id;

    private String profile;

    private String company;

    private String certificate;

    private String status;

    private String kakaoLink;

    private String plannerNickname;

    //user
    private String nickname;

    //promotion
    private PromotionDto promotion;

    //review
    private List<ReviewDto> review;

    //connectCategory
    private List<String> categoryName;

    //review Size

    @JsonIgnore
    private MultipartFile[] f = new MultipartFile[2];

    public double getAverageRating() {
        if (review != null) {
            double sum = review.stream().mapToInt(ReviewDto::getRate).sum();
            if (!review.isEmpty()) {
                double average = sum / review.size();
                // 평균을 소수점 한 자리까지 반올림하여 문자열로 변환
                return Double.parseDouble(String.format("%.1f", average));
            }
        }
        return 0.0; // 평점이 없는 경우 0으로 반환하거나 다른 값을 지정할 수 있습니다.
    }

}
