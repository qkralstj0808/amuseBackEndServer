package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.person_enum.Gender;

import javax.persistence.*;

@Entity(name = "tourist")
@Getter
@Setter
public class Tourist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String touristName;

    private Gender touristSex;

    private Boolean isBathchair;


    // tourist와 payment_info는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;


}
