package com.example.amusetravelproejct.dto.request;


import com.example.amusetravelproejct.domain.BaseEntity;
import com.example.amusetravelproejct.domain.PhoneCountryCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushPayInfoReq extends BaseEntity {

    private Long paymentInfoTypeId;

    private String reservationName;

    private String reservationEmail;

    private Long phoneCountryCodeId;

    private String phoneNumber;

    private Long itemId;

    private Long itemTicketDealId;

    private String additionalRequests;

    private Long originalPrice;

    private Long finalPrice;

    private Long usedPoints;

    private Long couponCodeId;


}
