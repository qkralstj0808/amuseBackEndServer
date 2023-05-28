package com.example.amusetravelproejct.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TargetUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // db의 고유 Id

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
}
