package com.example.amusetravelproejct.domain;


import com.example.amusetravelproejct.domain.person_enum.Adver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity(name = "admin_advertisement")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
public class AdminAdvertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String advertisementTitle;
    private Adver advertisementType;

    @Column(columnDefinition = "TEXT")
    private String advertisementContent;

    private Date advertisementStartDate;
    private Date advertisementEndDate;

    @CreatedDate
    private LocalDateTime createdAdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_admin")
    private Admin updateAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}