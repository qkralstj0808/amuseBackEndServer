package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_review_img")
@Getter
@Setter
public class ItemReviewImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

    // item_estimation과 estimation_img는 1:N 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_review_id")
    private ItemReview itemReview;
}
