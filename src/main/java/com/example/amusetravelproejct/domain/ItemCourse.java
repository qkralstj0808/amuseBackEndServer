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

    private String title;           // 코스 이름
    private String content;         // 코스 내용
    private Long sequenceId;        // 코스 순서
    private String timeCost;        // 해당 코스가 걸리는 시간
    private String imageUrl;        // 코스 사진
    private Double latitude;        // 코스 위치 위도
    private Double longitude;       // 코스 위치 경도

    // item_course와 iteminfo는 N:1 관계 ManyToOne
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "item_id")
     private Item item;


}
