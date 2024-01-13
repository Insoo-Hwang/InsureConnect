package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserDto {

    private UUID id;

    private Long kakaoId;

    private String nickname;

    private String gender;

    private int age;

    private String type;

    @JsonIgnore
    private PlannerDto planner;

    @JsonIgnore
    private ChatDto chat;

    private ReviewDto review;

}
