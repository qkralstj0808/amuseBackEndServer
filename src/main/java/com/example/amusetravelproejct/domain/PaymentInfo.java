package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.domain.person_enum.PayType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "payment_info")
@Getter
@Setter
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    private PayType payType;

    private Long cost;


    // iteminfo와 payment_info는 1:N 관계
    @ManyToOne
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;

    // user와 payment_info는 1:N 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // payment_info와 payment_ticket은 1:N 관계
    @OneToMany(mappedBy = "paymentInfo")
    private List<PaymentTicket> paymentTickets = new ArrayList<>();

    // payment_info와 tourist은 1:N 관계
    @OneToMany(mappedBy = "paymentInfo")
    private List<Tourist> tourists = new ArrayList<>();
}
