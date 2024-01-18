package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.UserDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {
    private static ModelMapper modelMapper = new ModelMapper();

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
    private List<Review> review;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Chat> chat;

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
