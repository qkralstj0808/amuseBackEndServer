package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_introduction_image")
@Getter
@Setter
public class ItemIntroductionImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

// item_introduction_image와 iteminfo는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
