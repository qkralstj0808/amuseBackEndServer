package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "admin")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String profileImgLink;

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




}
