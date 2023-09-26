package com.example.amusetravelproejct.dto.response.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPageResponseDto {

    private String userName;

    private String userPhoneNumber;

    private String userEmail;

    private Integer userPoint;
}
