package com.example.InsureConnect.Dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDto {

    private Long id;

    private Long plannerId;

    private String plannerNickname;

    private UserDto user;

    private List<ReviewImgDto> reviewImg;

    private String title;

    private String content;

    private int rate;

    private Timestamp write;

    private Timestamp edit;

}
