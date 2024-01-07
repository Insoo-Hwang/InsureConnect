package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.PromotionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
        promotionImages.add(promotionImg);
    }

    public void removePromotionImage(PromotionImg promotionImg) {
        promotionImages.remove(promotionImg);
    }

}
