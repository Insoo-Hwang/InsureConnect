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
public class ReviewDto {

    private Long id;

    private PlannerDto planner;

    private UserDto user;

    private List<ReviewImgDto> reviewImg;

    private String title;

    private String content;

    private int rate;

    private Timestamp write;

    private Timestamp edit;

}
