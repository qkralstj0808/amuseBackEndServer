package com.example.amusetravelproejct.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTicketRequestDto {

    private Long ticketId; // 티켓 id

    private Integer ticketCount; // 티켓 개수

}
