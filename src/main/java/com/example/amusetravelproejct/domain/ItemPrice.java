package com.example.amusetravelproejct.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_price")
@Getter
@Setter
public class ItemPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startDate;
    private String endDate;
    private String price;

    // iteminfo와 item_price는 1:N 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
