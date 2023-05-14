package com.example.amusetravelproejct.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.EnumMap;

@Entity(name = "discount_coupon_ownership")      //할인쿠폰 소유정보 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCouponOwnership extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupon_code")
    private Coupon couponCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "quantity", nullable = false)
    private Long quantity;


}
