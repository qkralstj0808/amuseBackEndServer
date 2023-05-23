package com.example.amusetravelproejct.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserCouponRes {

    private String couponName; //쿠폰명

    private Long quantity;  //쿠폰량

    private Long couponId;

    private Long discountType;

    private Long discountAmount;

    private String typeOrigin;

}
