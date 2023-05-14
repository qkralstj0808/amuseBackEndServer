package com.example.amusetravelproejct.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "like_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // iteminfo와 like_item는 1:N 관계
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

 // user와 like_item는 N:1 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setLikeItem(Item item, User user){
        this.item = item;
        this.user = user;
        user.addLikeItem(this);
    }


}
