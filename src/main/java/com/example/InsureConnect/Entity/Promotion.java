package com.example.InsureConnect.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "planner_id")
    private Planner planner;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private Timestamp write;

    @Column
    private Timestamp edit;

    public void setWriteToCurrentTime() {
        this.write = new Timestamp(System.currentTimeMillis());
    }

    public void setEditToCurrentTime() {
        this.edit = new Timestamp(System.currentTimeMillis());
    }

}
