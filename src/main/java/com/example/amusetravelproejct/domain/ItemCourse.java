package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_course")
@Getter
@Setter
public class ItemCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Long sequenceId;
    private Long timeCost;
    private String imageUrl;
    private Double latitude;
    private Double longitude;

    // item_course와 iteminfo는 N:1 관계 ManyToOne
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "item_id")
     private Item item;


}
