package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.domain.person_enum.WEEKDAY;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@NoArgsConstructor
@Entity(name = "item_ticket_price")
public class ItemTicketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startDate;
    private Long price;

    // payment_ticket과 item_ticket은 N:1 관계
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "itemTicket")
    private ItemTicket itemTicket;

    @Builder
    public ItemTicketPrice(String startDate, Long price, ItemTicket itemTicket) {
        this.startDate = startDate;
        this.price = price;
        this.itemTicket = itemTicket;
    }
}
