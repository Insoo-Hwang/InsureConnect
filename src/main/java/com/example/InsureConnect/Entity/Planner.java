package com.example.InsureConnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Column
    private String profile;

    @Column
    private String company;

    @Column
    private String certificate;

    @Column
    private String status;

    @Column
    private String kakaoLink;

    @Column
    private String plannerNickname;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "planner")
    @JsonBackReference
    private Promotion promotion;

    @OneToMany(mappedBy = "planner")
    @JsonBackReference
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "planner")
    @JsonBackReference
    private List<ConnectCategory> connectCategory = new ArrayList<>();

    public void changeStatus(boolean permit){
        if(permit) this.status = "permit";
        else this.status = "temp";
    }
}
