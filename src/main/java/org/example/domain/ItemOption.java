package org.example.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "item_option")
@Getter
@Setter
public class ItemOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emogi;
    private String content;

    // item_option과 iteminfo는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;
}
