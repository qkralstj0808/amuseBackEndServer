package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.w3c.dom.Text;

import javax.persistence.*;

@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Setter
@Getter
public class Guide extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String emile;
    private String imgUrl;
    @Column(unique = true)
    private String code;
    @Column(columnDefinition = "TEXT")
    private String introduce;

}
