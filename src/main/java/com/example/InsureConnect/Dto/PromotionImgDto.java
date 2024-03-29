package com.example.InsureConnect.Dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PromotionImgDto {
    private Long id;

    private String imgLink;

    private int sequence;

    //Promotion
    private Long promotionId;

}
