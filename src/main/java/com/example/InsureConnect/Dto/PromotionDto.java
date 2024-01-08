package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.PromotionImg;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("planner_id")
    private Long plannerId;
    private String title;
    private String content;
    private Timestamp write;
    private Timestamp edit;
}
