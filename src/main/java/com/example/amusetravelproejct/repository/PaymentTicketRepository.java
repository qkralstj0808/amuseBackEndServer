package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.PaymentTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTicketRepository extends JpaRepository<PaymentTicket, Long> {
}
