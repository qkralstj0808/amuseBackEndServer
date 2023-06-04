package com.example.amusetravelproejct.domain;


import com.example.amusetravelproejct.domain.person_enum.MainType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
public class PageComponent extends BaseEntity{

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String title;
    private String PcBannerUrl;
    private String PcBannerLink;

    private String MobileBannerUrl;
    private String MobileBannerLink;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private Admin admin;

    @ManyToOne
    private Admin updateAdmin;

    @OneToMany(mappedBy = "pageComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryPageComponent> categoryPageComponents;

    @OneToMany(mappedBy = "pageComponent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MainPage> mainPages;



}
