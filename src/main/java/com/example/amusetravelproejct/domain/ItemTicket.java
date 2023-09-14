package com.example.amusetravelproejct.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "item_ticket")
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

    @Builder
    public ItemTicket(String title, String content, Long sequenceNum, Item item, List<ItemTicketPrice> itemTicketPrices, List<ItemTicketPriceRecode> itemTicketPriceRecodes) {
        this.title = title;
        this.content = content;
        this.sequenceNum = sequenceNum;
        this.item = item;
        this.itemTicketPrices = itemTicketPrices;
        this.itemTicketPriceRecodes = itemTicketPriceRecodes;
    }

    public void updateItem(Item item) {
        this.item = item;
    }
}