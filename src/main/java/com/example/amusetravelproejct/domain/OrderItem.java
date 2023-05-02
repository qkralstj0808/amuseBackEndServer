package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // order와 order_item은 N:1 관계
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // user와 order_item은 1:N 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
