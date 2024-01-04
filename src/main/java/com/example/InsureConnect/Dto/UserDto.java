package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.User;
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

    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getKakaoId(), user.getNickname(), user.getGender(), user.getAge(), user.getType());
    }
}
