package com.example.InsureConnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_id",referencedColumnName = "id")
    @JsonManagedReference
    private Planner planner;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private Timestamp write;

    @Column
    private Timestamp edit;

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<PromotionImg> promotionImg = new ArrayList<>();

    public void setWriteToCurrentTime() {
        this.write = new Timestamp(System.currentTimeMillis());
    }

    public void setEditToCurrentTime() {
        this.edit = new Timestamp(System.currentTimeMillis());
    }

}
