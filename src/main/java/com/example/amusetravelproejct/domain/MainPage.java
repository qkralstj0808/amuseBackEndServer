package com.example.amusetravelproejct.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Where(clause = "item.display = true")
public class MainPage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "page_component_id")
    private PageComponent pageComponent;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tile_id")
    private Tile tile;

    private Integer sequence;
}
