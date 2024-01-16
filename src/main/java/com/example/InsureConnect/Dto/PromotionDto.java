package com.example.InsureConnect.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    private PlannerDto planner;

    private String title;

    private String content;

    private Timestamp write;

    private Timestamp edit;

    @JsonBackReference
    private List<PromotionImgDto> promotionImg;

}
