package com.example.InsureConnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonManagedReference
    private User user;

    @Column
    private Timestamp time;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatRoom")
    @JsonBackReference
    private List<Chat> chats = new ArrayList<>();

    @PrePersist
    private void generateUUID() {
        if (code == null) {
            code = UUID.randomUUID();
        }
        if (time == null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            time = now;
        }
    }
}
