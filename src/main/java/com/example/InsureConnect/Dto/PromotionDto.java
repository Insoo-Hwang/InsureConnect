package com.example.InsureConnect.Dto;

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
}
