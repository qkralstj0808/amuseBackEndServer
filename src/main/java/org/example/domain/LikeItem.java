package org.example.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "like_item")
@Getter
@Setter
public class LikeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // iteminfo와 like_item는 1:N 관계
    @ManyToOne
    @JoinColumn(name = "iteminfo_id")
    private Iteminfo iteminfo;

 // user와 like_item는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
