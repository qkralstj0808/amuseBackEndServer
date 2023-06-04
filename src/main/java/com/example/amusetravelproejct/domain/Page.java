package com.example.amusetravelproejct.domain;


import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Category category;


    private Long sequence;
    @ManyToOne
    private Admin admin;
//    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PageComponent> pageComponents;
}
