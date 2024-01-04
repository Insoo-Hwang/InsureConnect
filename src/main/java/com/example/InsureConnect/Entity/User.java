package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private Long kakaoId;

    @Column
    private String nickname;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private String type;

    @PrePersist
    private void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public static User toUser(UserDto dto){
        return new User(dto.getId(), dto.getKakaoId(), dto.getNickname(), dto.getGender(), dto.getAge(), dto.getType());
    }
}
