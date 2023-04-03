package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "item_ticket")
@Getter
@Setter
public class ItemTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemOptionTitle;
    @Lob
    @Column(name = "itemOptionContent", columnDefinition = "TEXT")
    private String itemOptionContent;

    private Long price;

    // item_ticket과 iteminfo는 N:1 관계 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Iteminfo iteminfo;

    // item_ticket과 payment_ticket은 1:N 관계
    @OneToMany(mappedBy = "itemTicket")
    private List<PaymentTicket> paymentTickets = new ArrayList<>();

}