package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    // category와 iteminfo는 1:N 관계
     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Item> items = new ArrayList<>();

     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<AdminAdvertisement> adminAdvertisements = new ArrayList<>();


}