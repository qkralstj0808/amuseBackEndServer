package com.example.amusetravelproejct.dto.request.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 결제 약관 동의 여부 Dto
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAgreementRequestDto {

    private Integer privacyCollection; // 개인정보 수집 및 이용 동의

    private Integer privacyToThirdParty; // 개인정보 제 3자 제공

    private Integer conciergeRule; // 컨시어지 이용규칙 동의

    private Integer ageOver14; // 만 14세이상 확인

    private Integer stayRule; // 숙소 이용 규칙
}
