package com.example.InsureConnect.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PromotionDto {
    private Long id;
    @JsonProperty("planner_id")
    private Long plannerId;
    private String title;
    private String content;
    private Timestamp write;
    private Timestamp edit;
    private List<PromotionImgDto> promotionImgDtos;
}
