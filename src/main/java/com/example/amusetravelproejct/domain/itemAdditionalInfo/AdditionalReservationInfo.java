package com.example.amusetravelproejct.domain.itemAdditionalInfo;

import com.example.amusetravelproejct.domain.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AdditionalReservationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 50000)
    private String content;

    @OneToMany(mappedBy = "item")
    private List<Item> items = new ArrayList<>();
}
