package com.example.amusetravelproejct.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity(name = "like_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class LikeItem extends BaseEntity {
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
