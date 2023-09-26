package com.example.amusetravelproejct.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
public class ItemTicketPriceRecode  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    private String startDate;
    private String endDate;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemTicket")
    private ItemTicket itemTicket;

    @Builder
    public ItemTicketPriceRecode(Long quantity, String startDate, String endDate, String mon, String tue, String wed, String thu, String fri, String sat, String sun, ItemTicket itemTicket) {
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.itemTicket = itemTicket;
    }

    public void updateItemTicket(ItemTicket itemTicket){
        this.itemTicket = itemTicket;
    }
}
