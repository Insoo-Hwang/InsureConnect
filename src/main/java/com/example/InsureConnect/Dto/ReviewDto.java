package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDto {

    private Long id;

    @JsonIgnore
    private PlannerDto planner;

    @JsonIgnore
    private UserDto user;

    private String title;

    private String content;

    private int rate;

    private Timestamp write;

    private Timestamp edit;

}
