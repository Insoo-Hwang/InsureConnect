package com.example.InsureConnect.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imgLink;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    public void setPromotion(Promotion promotion){
        this.promotion = promotion;
    }
}
