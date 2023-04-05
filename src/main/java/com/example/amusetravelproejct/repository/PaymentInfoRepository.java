package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
}
