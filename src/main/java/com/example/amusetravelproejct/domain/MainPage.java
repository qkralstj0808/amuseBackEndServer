package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
public class MainPage {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private MainPageComponent mainPageComponent;
    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;
    @ManyToOne(cascade = CascadeType.ALL)
    private Tile tile;
}
