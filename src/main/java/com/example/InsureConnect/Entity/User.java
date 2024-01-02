package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    public static User toUser(UserDto dto){
        return new User(dto.getId(), dto.getNickname());
    }
}
