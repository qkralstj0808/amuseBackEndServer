package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "item_course")
@Getter
@Setter
public class ItemCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemCourseTitle;
    private String itemCourseContent;
    private Long itemCourseSequenceId;
    private String itemImageUrl;
    private Double latitude;
    private Double longitude;

    // item_course와 iteminfo는 N:1 관계 ManyToOne
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "iteminfo_id")
     private Iteminfo iteminfo;










}
