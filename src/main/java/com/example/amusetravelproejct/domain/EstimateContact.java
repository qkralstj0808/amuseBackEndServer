package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "estimate_contact")
@Getter
@Setter
public class EstimateContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    // estimate_contact와 iteminfo는 1:N 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;

    // estimate_contact와 user는 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
