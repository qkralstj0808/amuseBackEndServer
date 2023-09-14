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
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Long sequenceNum;

    // item_ticket과 iteminfo는 N:1 관계 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // item_ticket과 payment_ticket은 1:N 관계
    @OneToMany(mappedBy = "itemTicket",orphanRemoval=true , cascade = CascadeType.ALL)
    private List<ItemTicketPrice> itemTicketPrices = new ArrayList<>();

    @OneToMany(mappedBy = "itemTicket",orphanRemoval=true , cascade = CascadeType.ALL)
    private List<ItemTicketPriceRecode> itemTicketPriceRecodes = new ArrayList<>();

}