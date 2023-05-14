package com.example.amusetravelproejct.dto.request;


import com.example.amusetravelproejct.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertCouponTypeReq extends BaseEntity {


    private String couponName;


    private String couponCode;


    private Integer discountType;


    private Long discountAmount;


}
