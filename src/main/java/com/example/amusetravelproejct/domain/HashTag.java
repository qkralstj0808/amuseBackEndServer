//package com.example.amusetravelproejct.domain;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity(name = "hash_tag")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class HashTag {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String hashTag;
//
//    @OneToMany(mappedBy = "hash_tag", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<ItemHashTag> categoryList = new ArrayList<>();
//
//}
