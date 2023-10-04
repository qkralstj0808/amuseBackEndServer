package com.example.amusetravelproejct.repository.itemAdditionalInfo;

import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentCancelPolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCancelPolicyInfoRepository extends JpaRepository<PaymentCancelPolicyInfo, Long> {
    PaymentCancelPolicyInfo findByType(String type);

    void deleteAllByType(String type);
}
