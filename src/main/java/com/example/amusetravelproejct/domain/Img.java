package com.example.amusetravelproejct.domain;

import com.example.amusetravelproejct.domain.person_enum.ImgRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "img")
@Getter
@Setter
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    private ImgRole imgRole;


  // img와 iteminfo는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;

}

