package org.example.domain;

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


  // img와 iteminfo는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;











}

