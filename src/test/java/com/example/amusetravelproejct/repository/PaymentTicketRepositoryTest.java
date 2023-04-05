package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.PaymentInfo;
import com.example.amusetravelproejct.domain.person_enum.PayType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentTicketRepositoryTest {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Test
    public void create() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setStartDate(new Date());
        paymentInfo.setPayType(PayType.KakaoPay);
        paymentInfo.setCost(10000L);

        PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);
        assertNotNull(savedPaymentInfo.getId());
    }

    @Test
    public void read() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setStartDate(new Date());
        paymentInfo.setPayType(PayType.KakaoPay);
        paymentInfo.setCost(10000L);

        PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);
        Long paymentInfoId = savedPaymentInfo.getId();

        PaymentInfo foundPaymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);
        assertNotNull(foundPaymentInfo);
    }

    @Test
    public void update() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setStartDate(new Date());
        paymentInfo.setPayType(PayType.KakaoPay);
        paymentInfo.setCost(10000L);

        PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);
        Long paymentInfoId = savedPaymentInfo.getId();

        PaymentInfo foundPaymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);
        assertNotNull(foundPaymentInfo);

        foundPaymentInfo.setCost(20000L);
        PaymentInfo updatedPaymentInfo = paymentInfoRepository.save(foundPaymentInfo);
        assertEquals(Long.valueOf(20000L), updatedPaymentInfo.getCost());
    }

    @Test
    public void delete() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setStartDate(new Date());
        paymentInfo.setPayType(PayType.KakaoPay);
        paymentInfo.setCost(10000L);

        PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);
        Long paymentInfoId = savedPaymentInfo.getId();

        paymentInfoRepository.deleteById(paymentInfoId);

        PaymentInfo deletedPaymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);
        assertNull(deletedPaymentInfo);
    }

}