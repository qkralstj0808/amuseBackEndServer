package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    @CreatedDate
    private LocalDateTime createdAdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    // category와 iteminfo는 1:N 관계
     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Item> items = new ArrayList<>();

     @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<AdminAdvertisement> adminAdvertisements = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

}