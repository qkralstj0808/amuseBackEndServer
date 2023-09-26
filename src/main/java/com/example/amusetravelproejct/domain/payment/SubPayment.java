package com.example.amusetravelproejct.domain.payment;

import com.example.amusetravelproejct.domain.BaseEntity;
import com.example.amusetravelproejct.domain.person_enum.CardType;
import com.example.amusetravelproejct.domain.person_enum.PayStatus;
import com.example.amusetravelproejct.domain.person_enum.PayType;
import com.example.amusetravelproejct.domain.person_enum.ReservationType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "subPayment")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubPayment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestComment; // 추가 요청 사항

    private Integer itemCost; // 상품 가격 정보

    private String reservationNumber; // 예약번호

    private  Long discountRate; // 할인 정도

    @Enumerated(value = EnumType.STRING)
    private PayType payType; // 결제 방법

    private Integer pointAcquire; // 포인트 적립 정도

    @Enumerated(value = EnumType.STRING)
    private CardType cardType; // 카드종류

    private String CardNumber; // 카드 번호

    private String bookerName; // 예약자 이름

    private String bookerBirthDay; // 예약자생년월일

    private String bookerEnglishFirstName; // 예약자 영문이름

    private String bookerEnglishLastName; // 예약자 영문 성

    private String bookerPhoneNumber; // 예약자 전화번호

    private String bookerEmail; // 예약자 이메일

    private String passportNumber; // 여권 번호

    private String stayerName; // 투숙객 이름

    private String stayerBirthDay; // 투숙객 생년월일

    private String stayerEnglishFirstName; // 투숙객 영문이름

    private String stayerEnglishLastName; // 투숙객 영문성

    private String stayerPhoneNumber; // 투숙객 전화번호

    private String stayerEmail; // 투숙객 이메일

    @Enumerated(value = EnumType.STRING)
    private PayStatus payStatus; // 결제상태

    @Enumerated(value = EnumType.STRING)
    private ReservationType reservationType; //예약방법

    private String itemMainContent; // 상품 상세 설명

    private String itemSubContent; // 상품 부가 설명

    /**
     * 연관관계
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    @OneToMany(mappedBy = "subPayment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketInformation> ticketInformationList = new ArrayList<>();
}
