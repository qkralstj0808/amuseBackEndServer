package org.example.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "item_estimation")
@Getter
@Setter
public class ItemEstimation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rating;
    private String reviewContent;

    // item_estimation과 iteminfo는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;

    // user와 item_estimation은 1:N 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // item_estimation과 estimation_img은 1:N 관계
    @OneToMany(mappedBy = "itemEstimation")
    private List<EstimationImg> estimationImgs = new ArrayList<>();





}
