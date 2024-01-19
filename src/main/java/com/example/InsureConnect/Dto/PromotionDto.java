package com.example.InsureConnect.Dto;

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

    private String title;

    private String content;

    private Timestamp write;

    private Timestamp edit;

    //Planner
    private Long plannerId;

    //PromotionImg
    private List<PromotionImgDto> promotionImg;

}
