package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/*
    각각의 상품에 hash_tag들을 저장하는 table
    나중에 category에 있는 카테고리 명으로 search해서 해당 카테고리의 이름과 일치하는 hash_tag가 있는 상품을 들고와서
    카테고리별 페이지에 보여줄 예정

 */

@Entity(name = "item_hash_tag")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
public class ItemHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hashTag;

    @OneToOne(fetch = FetchType.LAZY)
    private TempHashTag tempHashTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


}

