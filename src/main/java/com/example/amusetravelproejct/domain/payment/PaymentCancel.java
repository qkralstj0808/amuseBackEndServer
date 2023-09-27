package com.example.amusetravelproejct.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity(name = "paymentCancel")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCancel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
}
