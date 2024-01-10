package com.example.InsureConnect.Dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PromotionImgDto {
    private Long id;
    private String imgLink;
    private int sequence;
}
