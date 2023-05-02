package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_add_option")
@Getter
@Setter
public class ItemAddOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;


    // item_add_option과 iteminfo는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;


}
