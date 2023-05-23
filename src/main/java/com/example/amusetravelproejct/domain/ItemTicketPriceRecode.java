package com.example.amusetravelproejct.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
public class ItemTicketPriceRecode  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

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
}
