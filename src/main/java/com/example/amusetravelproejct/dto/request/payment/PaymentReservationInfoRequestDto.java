package com.example.amusetravelproejct.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReservationInfoRequestDto {

    private String bookerName; // 예약자 이름

    private String bookerBirthDay; // 예약자생년월일

    private String bookerEnglishFirstName; // 예약자 영문이름

    private String bookerEnglishLastName; // 예약자 영문 성

    private String bookerPhoneNumber; // 예약자 전화번호

    private String bookerEmail; // 예약자 이메일

    private String passportNumber; // 여권 번호
}
