package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Chat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDto {
    private Long id;

    @JsonManagedReference
    private UserDto user;

    private String question;

    private String answer;

    private Timestamp time;

}
