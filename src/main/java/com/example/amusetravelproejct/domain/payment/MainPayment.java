package com.example.amusetravelproejct.domain.payment;

import com.example.amusetravelproejct.domain.BaseEntity;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.person_enum.PayStatus;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "mainPayment")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPayment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String itemImage;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate travelStartDate; // 여행 시작 날짜

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate travelEndDate; // 여행 종료 날짜

    @Enumerated(value = EnumType.STRING)
    private PayStatus payStatus; // 결제 상태 SUCCESS, CANCEL, PENDING

    private Integer itemPayPrice; // 결제 당시 상품의 결제 가격

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Builder.Default
    private LocalDateTime reservationDateTime = LocalDateTime.now(); // 결제(예약)날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_payment_id")
    SubPayment subPayment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    Item item;


}
