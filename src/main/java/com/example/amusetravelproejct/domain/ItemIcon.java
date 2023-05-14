package com.example.amusetravelproejct.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "item_icon")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ItemIcon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemIcon_ID")
    private Long id;

    private String text;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "icon_id")
    private Icon icon;

}
