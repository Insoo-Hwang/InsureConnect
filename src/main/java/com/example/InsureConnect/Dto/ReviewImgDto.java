package com.example.InsureConnect.Dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewImgDto {
    private Long id;

    private String imgLink;

    private int sequence;

    private Long reviewId;
}
