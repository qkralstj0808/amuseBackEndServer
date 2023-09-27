package com.example.amusetravelproejct.dto.request.payment;

import com.example.amusetravelproejct.domain.person_enum.CardType;
import com.example.amusetravelproejct.domain.person_enum.PayStatus;
import com.example.amusetravelproejct.domain.person_enum.PayType;
import com.example.amusetravelproejct.domain.person_enum.ReservationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompleteRequestDto {

    private Long itemId;

    private List<PaymentTicketRequestDto> paymentTicketRequestDtoList; // 티켓정보Dto

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate travelStartDate; // 여행 시작 날짜

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate travelEndDate; // 여행 종료 날짜

    private PaymentReservationInfoRequestDto paymentReservationInfoDto; // 예약 정보 Dto

    private PaymentStayerInfoRequestDto paymentStayerInfoDto; // 투숙객 정보 Dto

    private String additionalRequest; // 추가 요청 사항

    private Integer itemCost; // 상품 가격 정보

    private Integer itemPayPrice; // 아이템 결제 가격

    @Enumerated(value = EnumType.STRING)
    private PayType payType; // 결제 방법

    private Integer pointAcquire; // 포인트 적립 정도

    private Integer pointUse; // 포인트 사용 정도

    @Enumerated(value = EnumType.STRING)
    private CardType cardType; // 카드종류

    private String CardNumber; // 카드 번호

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private PayStatus payStatus = PayStatus.SUCCESS; // 결제상태

    @Enumerated(value = EnumType.STRING)
    private ReservationType reservationType; //예약방법

    private PaymentAgreementRequestDto paymentAgreementRequestDto; // 약관 동의 여부 Dto

    private PaymentCancelDto paymentCancelDto; // 결제 취소 규정 Dto
}
