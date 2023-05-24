package com.example.amusetravelproejct.domain;


import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "item_review")
@Getter
@Setter
@EntityListeners(value = {AuditingEntityListener.class})
public class ItemReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float rating;
    private String content;

    // item_estimation과 iteminfo는 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // user와 iem_review 1:N 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // item_estimation과 estimation_img은 1:N 관계
    @OneToMany(mappedBy = "itemReview",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemReviewImg> itemReviewImgs = new ArrayList<>();





}
