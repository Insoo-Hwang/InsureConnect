package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Promotion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PromotionDto {
    private Long id;

    @JsonIgnore
    private PlannerDto planner;

    private String title;

    private String content;

    private Timestamp write;

    private Timestamp edit;

    private List<PromotionImgDto> promotionImg;

}
