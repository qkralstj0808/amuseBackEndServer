package com.example.amusetravelproejct.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@Entity(name = "category_pageComponent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class CategoryPageComponent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // user와 like_item는 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_component_id")
    private PageComponent pageComponent;

    private Long sequence;

}
