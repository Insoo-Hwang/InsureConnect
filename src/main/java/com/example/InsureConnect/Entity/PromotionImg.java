package com.example.InsureConnect.Entity;

import com.example.InsureConnect.Dto.PromotionImgDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromotionImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String imgLink;

    public static PromotionImg toPromotionImg(PromotionImgDto dto) {
        return new PromotionImg(dto.getId(), dto.getImgLink());
    }
}
