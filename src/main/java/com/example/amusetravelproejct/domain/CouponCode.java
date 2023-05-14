package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.domain.person_enum.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.EnumMap;

@Entity(name = "coupon_code")      //할인쿠폰 종류 테이블
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCode extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_amount", nullable = false)
    private Long discountAmount;
}
