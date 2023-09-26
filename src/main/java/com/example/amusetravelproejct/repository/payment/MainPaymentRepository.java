package com.example.amusetravelproejct.repository.payment;

import com.example.amusetravelproejct.domain.payment.MainPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPaymentRepository extends JpaRepository<MainPayment, Long> {
}
