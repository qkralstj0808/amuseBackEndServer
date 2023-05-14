package com.example.amusetravelproejct.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "phone_country_code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneCountryCode extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String countryCode;

}
