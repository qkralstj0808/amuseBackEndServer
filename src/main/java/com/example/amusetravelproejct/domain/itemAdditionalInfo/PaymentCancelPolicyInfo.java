package com.example.amusetravelproejct.domain.itemAdditionalInfo;

import com.example.amusetravelproejct.domain.Item;

import javax.persistence.*;

@Entity
public class PaymentCancelPolicyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 50000)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    Item item;
}
