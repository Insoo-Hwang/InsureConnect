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

    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromotionImg> promotionImages = new ArrayList<>();

    @PrePersist
    public void writePromotion(){
        this.write = new Timestamp(System.currentTimeMillis());
        this.edit = this.write;
    }

    @PreUpdate
    public void editPromotion(){
        this.edit = new Timestamp(System.currentTimeMillis());
    }

    public void addPromotionImage(PromotionImg promotionImg) {
        if(this.promotionImages == null){
            this.promotionImages = new ArrayList<>();
        }
        promotionImages.add(promotionImg);
    }

    public void removePromotionImage(PromotionImg promotionImg) {
        promotionImages.remove(promotionImg);
    }

}
