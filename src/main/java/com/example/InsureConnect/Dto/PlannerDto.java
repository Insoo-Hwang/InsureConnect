package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Planner;
import com.example.InsureConnect.Entity.User;
import lombok.*;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlannerDto {

    private Long id;

    private UserDto userDto;

    private String profile;

    private String company;

    private String certificate;

    private String status;

    private PromotionDto promotionDto;

    private List<ReviewDto> reviewDto;

    private MultipartFile[] f = new MultipartFile[2];

    public static PlannerDto toDto(Planner planner) {
        return PlannerDto.builder()
                .id(planner.getId())
                .userDto(UserDto.toDto(planner.getUser()))
                .profile(planner.getProfile())
                .company(planner.getCompany())
                .certificate(planner.getCertificate())
                .status(planner.getStatus())
                .promotionDto(PromotionDto.toDto(planner.getPromotion()))
                .reviewDto(ReviewDto.toDtoList(planner.getReviews())).build();
    }
}
