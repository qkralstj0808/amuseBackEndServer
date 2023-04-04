package org.example.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "admin_advertisement")
@Getter
@Setter
public class AdminAdvertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String advertisementTitle;
    private String advertisementContent;
    private LocalDateTime advertisementStartDate;
    private LocalDateTime advertisementEndDate;
}