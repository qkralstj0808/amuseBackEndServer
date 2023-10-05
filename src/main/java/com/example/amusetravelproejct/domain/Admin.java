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


@Builder
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

    private String adminId;
    private String password;
    private String name;

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
    private List<PageComponent> pageComponents = new ArrayList<>();


    @OneToMany(mappedBy = "updateAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PageComponent> updatePageComponents = new ArrayList<>();


}
