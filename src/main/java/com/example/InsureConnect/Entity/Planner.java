package com.example.InsureConnect.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Column
    private String profile;

    @Column
    private String company;

    @Column
    private String certificate;

    @Column
    private String status;

    @OneToOne(mappedBy = "planner")
    private Promotion promotion;

    @OneToMany(mappedBy = "planner")
    private List<Review> reviews;

    public void changeStatus(boolean permit){
        if(permit) this.status = "permit";
        else this.status = "temp";
    }
}
