package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.domain.person_enum.Gender;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "USERNAME", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", length = 128)
//    @NotNull
    @Size(max = 128)
    private String password;

    // 이메일을 중복해서 사용할 수 있도록 왜냐하면 Kakao로 로그인 사용자가 google 이메일을 쓸 경우 google로 다시 회원가입을 못하는 경우가 생긴다.
    @Column(name = "EMAIL", length = 512)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @Column(name = "PROFILE_IMAGE_URL", length = 512)
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

//    @OneToOne(mappedBy = "user")
//    private Admin admin;

    @Column(name = "POINT")     //유저가 지닌 포인트
    private Long point = 0L;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    private Boolean emailReceptionTrue;
    private Boolean messageReceptionTrue;

    @Builder.Default()
    @ColumnDefault("0")
    private Boolean advertisementTrue = false;

    @Builder.Default()
    @ColumnDefault("0")
    private Boolean over14AgeTrue = false;
    @Enumerated(EnumType.STRING)
    private Grade grade;                    // user 등급 (브론즈,실버)...

    private String auth;

    // user와 like_item는 1:N 관계
    @OneToMany(mappedBy = "user", cascade =CascadeType.ALL,orphanRemoval = true)
    private List<LikeItem> likeItems = new ArrayList<>();

    // user와 order_item는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // user와 item_estimation는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemReview> itemReviews = new ArrayList<>();

    // user와 payment_info는 1:N 관계
    @OneToMany(mappedBy = "user")
    private List<PaymentInfo> paymentInfos = new ArrayList<>();

    // user와 user_alarm는 1:N 관계
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAlarm> userAlarms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TargetUser> targetUsers = new ArrayList<>();
    public User(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String emailVerifiedYn,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType
    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.grade = Grade.BRONZE;
    }

    public void addLikeItem(LikeItem likeItem){
        this.likeItems.add(likeItem);
    }

    public void deleteLikeItem(LikeItem likeItem){
        this.likeItems.remove(likeItem);
    }

}
