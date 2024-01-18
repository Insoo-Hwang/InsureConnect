package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.ReviewDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "planner")
    @JsonBackReference
    private Promotion promotion;

    @OneToMany(mappedBy = "planner")
    @JsonBackReference
    private List<Review> review;

    @OneToMany(mappedBy = "planner")
    @JsonBackReference
    private List<ConnectCategory> connectCategory;

    public void changeStatus(boolean permit){
        if(permit) this.status = "permit";
        else this.status = "temp";
    }

    public double getAverageRating() {
        if (review != null && !review.isEmpty()) {
            double sum = review.stream().mapToInt(Review::getRate).sum();
            double average = sum / review.size();

            // 평균을 소수점 한 자리까지 반올림하여 문자열로 변환
            return Double.parseDouble(String.format("%.1f", average));
        }
        return 0.0; // 평점이 없는 경우 0으로 반환하거나 다른 값을 지정할 수 있습니다.
    }
}
