package com.example.amusetravelproejct.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "item_price")
@Getter
@Setter
public class ItemPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Long price;

    // iteminfo와 item_price는 1:N 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;
}
