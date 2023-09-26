package com.example.amusetravelproejct.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity(name = "ticketInformation")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@RequiredArgsConstructor
public class TicketInformation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketName; // 티켓 제목

    private String ticketSubName; // 티켓 부제목

    private String ticketPrice; // 티켓 가격

    private Integer ticketCount; // 티켓개수

    /**
     * 연관관계
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_payment_id")
    SubPayment subPayment;

}
