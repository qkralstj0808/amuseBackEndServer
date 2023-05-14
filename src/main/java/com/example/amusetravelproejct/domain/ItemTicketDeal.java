package com.example.amusetravelproejct.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_ticket_deal")      //티켓거래정보 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTicketDeal extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_ticket")
    private ItemTicket itemTicket;

    @Column(nullable = false)
    private Long ticketCount;
}
