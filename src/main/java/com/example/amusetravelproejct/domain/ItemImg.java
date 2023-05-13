package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.domain.person_enum.ImgRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "item_img")
@Getter
@Setter
public class ItemImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgUrl;

  // img와 iteminfo는 N:1 관계
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

}

