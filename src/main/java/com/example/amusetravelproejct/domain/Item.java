package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.AdditionalReservationInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentCancelPolicyInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.PaymentMethodInfo;
import com.example.amusetravelproejct.domain.itemAdditionalInfo.TermsOfServiceInfo;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "item")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Where(clause = "display = true")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // db의 고유 Id

    @Column(unique = true)
    private String itemCode;            // 상품 코드
    private String country;             // 나라
    private String city;                // 시 (강원도 까지만 하기로 했습니다!)

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content_1;           // html을 String으로 바꾼 내용 1번째

    @Column(columnDefinition = "LONGTEXT")
    private String content_2;           // html을 String으로 바꾼 내용 2번째 취소 내용

    @ColumnDefault("0")
    private Double rated;               // 모든 리뷰들 평점의 평균

    @ColumnDefault("0")
    private Integer review_count;
    private Long startPrice;            // 관리자가 정하는 시작 가격
    private Integer duration;           // 기간 (2박 3일 에서 3)

    @ColumnDefault("0")
    private Integer like_num;           // 좋아요 수

    private Date startDate;             // 상품 게시 날짜 (없어도 됨)
    private Date endDate;               // 상품 내리는 날짜 (없어도 됨)

    @Column(columnDefinition = "LONGTEXT")
    private String adminContent;

    @Enumerated(EnumType.ORDINAL)
    private Grade grade;                // 등급 (일반, 프리미엄, VIP)

    @ColumnDefault("0")
    private Integer viewCount;          // 조회수


//    @Enumerated(EnumType.STRING)
//    private DisplayStatus displayStatus; // 상품 노출 여부

    private Boolean display;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_admin")
    private Admin updateAdmin;

    // 가이드는 상품 하나당 한명
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "guide_id")
    private Guide guide;

    private String guide_comment;

    // 상세페이지에 보여주는 시작점, 진행시간, 활동강도
    private String startPoint;
    private String runningTime;
    private String activityIntensity;
    private String language;

    // item와 category는 N:1 관계 ManyToOne

    // item와 ItemImg 1:N 관계
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImg> itemImg_list = new ArrayList<>();


    // item에는 여러개 티켓이 있다.
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTicket> itemTickets = new ArrayList<>();

    // item와 item_course는 1:N 관계
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCourse> itemCourses = new ArrayList<>();

    // item와 item_hash_tag는 1:N 관계
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemHashTag> itemHashTags = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MainPage> mainPages = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemReview> itemReviews = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TargetUser> targetUsers = new ArrayList<>();

    // 결제 창 관련 정보들

    // 추가 예약 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_info")
    private AdditionalReservationInfo additionalReservationInfo;

    // 결제 취소 정책
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancel_policy_info")
    private PaymentCancelPolicyInfo paymentCancelPolicyInfo;

    // 결제 방법
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_info")
    private PaymentMethodInfo paymentMethodInfo;

    // 이용 약관
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terms_of_service_info")
    private TermsOfServiceInfo termsOfServiceInfo;

    // 로직
    public void plus_like() {

        this.like_num++;
    }

    public void minus_like(){
        this.like_num--;
    }

    private void ensureLikeOver0(){
        if(this.like_num < 0){
            throw new CustomException(ErrorCode.LIKE_UNDER_0);
        }
    }

    // 연관관계 편의 메소드 : 양방향 셋팅
    public void changeAdditionalReservationInfo(AdditionalReservationInfo additionalReservationInfo) {
        this.additionalReservationInfo = additionalReservationInfo;
        if (additionalReservationInfo != null) {
            additionalReservationInfo.getItems().add(this);
        }

    }

    public void changePaymentCancelPolicyInfo(PaymentCancelPolicyInfo paymentCancelPolicyInfo) {
        this.paymentCancelPolicyInfo = paymentCancelPolicyInfo;
        if (paymentCancelPolicyInfo != null) {
            paymentCancelPolicyInfo.getItems().add(this);
        }
    }

    public void changePaymentMethodInfo(PaymentMethodInfo paymentMethodInfo) {
        this.paymentMethodInfo = paymentMethodInfo;
        if (paymentMethodInfo != null) {
            paymentMethodInfo.getItems().add(this);
        }


    }

    public void changeTermsOfServiceInfo(TermsOfServiceInfo termsOfServiceInfo) {
        this.termsOfServiceInfo = termsOfServiceInfo;
        if (termsOfServiceInfo != null) {
            termsOfServiceInfo.getItems().add(this);
        }

    }
}
