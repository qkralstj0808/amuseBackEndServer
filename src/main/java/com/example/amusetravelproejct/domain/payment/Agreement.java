package com.example.amusetravelproejct.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
// 0: 미동의, 1:동의
@Entity(name = "agreement")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@RequiredArgsConstructor
public class Agreement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer privacyCollection; // 개인정보 수집 및 이용 동의

    private Integer privacyToThirdParty; // 개인정보 제 3자 제공

    private Integer conciergeRule; // 컨시어지 이용규칙 동의

    private Integer ageOver14; // 만 14세이상 확인

    private Integer stayRule; // 숙소 이용 규칙
}
