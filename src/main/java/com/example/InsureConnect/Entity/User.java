package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column
    private String nickname;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private String type;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "user")
    @JsonBackReference
    private Planner planner;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Chat> chat = new ArrayList<>();

    @PrePersist
    private void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public void patch(UserDto userDto) {
        this.nickname = userDto.getNickname();
        this.age = userDto.getAge();
        this.gender = userDto.getGender();
    }

    public void delete(){
        this.kakaoId = null;
    }
}
