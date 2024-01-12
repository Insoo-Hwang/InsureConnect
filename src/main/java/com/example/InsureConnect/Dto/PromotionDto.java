package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Promotion;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PromotionDto {
    private Long id;

    private Long planner_id;

    private String title;

    private String content;

    private Timestamp write;

    private Timestamp edit;

    public static PromotionDto toDto(Promotion promotion) {
        return PromotionDto.builder()
                .id(promotion.getId())
                .planner_id(promotion.getPlanner().getId())
                .title(promotion.getTitle())
                .content(promotion.getContent())
                .write(promotion.getWrite())
                .edit(promotion.getEdit())
                .build();
    }
}
