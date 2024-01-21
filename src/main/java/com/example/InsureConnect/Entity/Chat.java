package com.example.InsureConnect.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JoinColumn(name = "chatRoom_id",referencedColumnName = "id")
    @JsonManagedReference
    private ChatRoom chatRoom;

    @Column
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column
    private Timestamp time;
}
