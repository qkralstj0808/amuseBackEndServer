package com.example.amusetravelproejct.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStayerInfoRequestDto {

    private String stayerName; // 투숙객 이름

    private String stayerBirthDay; // 투숙객 생년월일

    private String stayerEnglishFirstName; // 투숙객 영문이름

    private String stayerEnglishLastName; // 투숙객 영문성

    private String stayerPhoneNumber; // 투숙객 전화번호

    private String stayerEmail; // 투숙객 이메일
}
