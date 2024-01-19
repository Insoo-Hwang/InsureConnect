package com.example.InsureConnect.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
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

    public void deleteStatus(){
        this.status = "deleted";
    }

    public int getRecommendRating(){
        int score = 0;
        if(review != null && !review.isEmpty()){
            int cnt = 0;
            for(Review reviewScore : review){
                Timestamp before = reviewScore.getWrite();
                Timestamp now = new Timestamp(System.currentTimeMillis());
                Instant beforeIns = before.toInstant();
                Instant nowIns = now.toInstant();
                Duration duration = Duration.between(beforeIns, nowIns);
                long day = Math.abs(duration.toDays());
                if(day > 365) continue;
                else if(day > 180){
                    score+=reviewScore.getRate()*20;
                    cnt++;
                }
                else if(day > 90){
                    score+=reviewScore.getRate()*33;
                    cnt++;
                }
                else if(day > 30){
                    score+=reviewScore.getRate()*44;
                    cnt++;
                }
                else{
                    score+=reviewScore.getRate()*58;
                    cnt++;
                }
            }
            score = (score/cnt)+(cnt*20);
        }
        return score;
    }
}
