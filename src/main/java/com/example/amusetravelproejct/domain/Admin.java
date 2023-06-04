package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "admin")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADMIN_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;
//
//    @Column(name = "ROLE_TYPE", length = 20)
//    @Enumerated(EnumType.STRING)
//    @NotNull
//    private RoleType roleType;

//    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
//    @NotNull
//    @Size(min = 1, max = 1)
//    private String emailVerifiedYn;

    private String email;
    private String password;
    private String name;
    private String profileImgLink;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // admin과 supervisor_info는 1:N 관계
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupervisorInfo> supervisorInfos = new ArrayList<>();

    // admin과 admin_advertisement는 1:N 관계

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Advertisement> Advertisements = new ArrayList<>();

    @OneToMany(mappedBy = "updateAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Advertisement> AdvertisementUpdates = new ArrayList<>();

    // admin과 alarm은 1:N 관계
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "updateAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itemUpdates = new ArrayList<>();


    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> displayCategories = new ArrayList<>();

    @OneToMany(mappedBy = "updateAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> displayCategoriesUpdates = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MainPageComponent> mainPageComponents = new ArrayList<>();



}
