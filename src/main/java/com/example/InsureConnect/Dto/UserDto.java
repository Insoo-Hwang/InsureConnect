package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserDto {
    private long id;
    private String nickname;

    public static UserDto toDto(User user){
        return new UserDto(user.getId(), user.getNickname());
    }
}
