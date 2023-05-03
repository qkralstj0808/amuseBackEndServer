package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemTicket;
import com.example.amusetravelproejct.domain.PaymentTicket;
import com.example.amusetravelproejct.repository.PaymentTicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PaymentTicketService {
    private final PaymentTicketRepository paymentTicketRepository;

    public PaymentTicket savePaymentTicket(PaymentTicket paymentTicket) {
        return paymentTicketRepository.save(paymentTicket);
    }
}
