package com.example.amusetravelproejct.domain;


import javax.persistence.*;

import com.example.amusetravelproejct.domain.person_enum.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private String profileImgLink;
    private String email;
    private String name;
    private LocalDate birthday;
    private Gender gender;
    private Boolean emailReceptionTrue;
    private Boolean messageReceptionTrue;
    private Boolean isMember;

    // user와 customer_question는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CustomerQuestion> customerQuestions = new ArrayList<>();

    // user와 like_item는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LikeItem> likeItems = new ArrayList<>();

    // user와 estimate_contact는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EstimateContact> estimateContacts = new ArrayList<>();

    // user와 order_item는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    // user와 item_estimation는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemEstimation> itemEstimations = new ArrayList<>();

    // user와 payment_info는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PaymentInfo> paymentInfos = new ArrayList<>();

    // user와 user_alarm는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserAlarm> userAlarms = new ArrayList<>();
}






























