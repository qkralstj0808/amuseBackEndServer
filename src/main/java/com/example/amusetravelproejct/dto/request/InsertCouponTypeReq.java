package com.example.amusetravelproejct.dto.request;


import com.example.amusetravelproejct.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertCouponTypeReq extends BaseEntity {


    @NotNull
    private String couponName;


    @NotNull
    private String couponCode;

    @NotNull
    private Integer discountType;

    @NotNull
    private Long discountAmount;


}
