package org.example.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "estimation_img")
@Getter
@Setter
public class EstimationImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

    // item_estimation과 estimation_img는 1:N 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_estimation_id")
    private ItemEstimation itemEstimation;
}
