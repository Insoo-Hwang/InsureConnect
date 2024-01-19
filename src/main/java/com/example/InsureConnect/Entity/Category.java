package com.example.InsureConnect.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<ConnectCategory> connectCategory = new ArrayList<>();

    
    public void setConnectCategory(List<ConnectCategory> connectCategory) {
        this.connectCategory = connectCategory;
    }
}
