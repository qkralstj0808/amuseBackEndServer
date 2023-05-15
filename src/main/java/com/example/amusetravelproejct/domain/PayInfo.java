package com.example.amusetravelproejct.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "pay_info")  //구매정보
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info_type")
    private PaymentInfoType paymentInfoType;

    @Column(name = "reservation_name", nullable = false)
    private String reservationName;

    @Column(name = "reservation_email", nullable = false)
    private String reservationEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_country_code")
    private PhoneCountryCode phoneCountryCode;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_ticket_deal")
    private ItemTicketDeal itemTicketDeal;

    @Column(name = "additional_requests", nullable = false)
    private String additionalRequests;

    @Column(name = "original_price", nullable = false)
    private Long originalPrice;

    @Column(name = "final_price", nullable = false)
    private Long finalPrice;

    @Column(name = "used_points", nullable = false)
    private Long usedPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_code_id", referencedColumnName = "id")
    private Coupon couponCode;

}
