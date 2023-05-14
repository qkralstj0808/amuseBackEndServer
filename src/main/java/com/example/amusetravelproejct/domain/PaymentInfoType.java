package com.example.amusetravelproejct.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "payment_info_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoType extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "method_name", nullable = false)
    private String methodName;
}
