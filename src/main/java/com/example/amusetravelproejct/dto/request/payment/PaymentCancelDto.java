package com.example.amusetravelproejct.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 결제 취소 규정 Dto
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelDto {

    private String content; // 일단 내용 String으로 때려받기
}
