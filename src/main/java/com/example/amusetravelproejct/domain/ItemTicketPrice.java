package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "item_ticket_price")
@Getter
@Setter
public class ItemTicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startDate;
    private String price;

    // payment_ticket과 item_ticket은 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemTicket")
    private ItemTicket itemTicket;
//
//    // payment_ticket과 payment_info는 N:1 관계
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_info_id")
//    private PaymentInfo paymentInfo;
}
