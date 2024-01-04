package com.example.InsureConnect.Dto;

import com.example.InsureConnect.Entity.Chat;
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
    @JsonProperty("user_id")
    private UUID userId;
    private String question;
    private String answer;
    private Timestamp time;

    public static ChatDto toDto(Chat chat){
        return new ChatDto(chat.getId(), chat.getUser().getId(), chat.getQuestion(), chat.getAnswer(), chat.getTime());
    }

    public static ChatDto testDto(Chat chat){
        UUID uuid = UUID.randomUUID();
        return new ChatDto(chat.getId(), uuid, chat.getQuestion(), chat.getAnswer(), chat.getTime());
    }
}
