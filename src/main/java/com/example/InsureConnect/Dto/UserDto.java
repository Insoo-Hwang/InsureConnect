package com.example.InsureConnect.Dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private UUID id;

    private Long kakaoId;

    private String nickname;

    private String email;

    private String gender;

    private int age;

    private String type;

    private Long plannerId;

    private List<Long> chatId;

    private List<Long> reviewId;

}
