package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.ChatDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column
    private Timestamp time;

    public static Chat toChat(ChatDto dto, User user){
        return new Chat(dto.getId(), user, dto.getQuestion(), dto.getAnswer(), dto.getTime());
    }
}
