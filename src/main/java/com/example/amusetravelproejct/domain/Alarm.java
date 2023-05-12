package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "alarm")
@EntityListeners(value = {AuditingEntityListener.class})

@Getter
@Setter
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subTitle;
    private String content;

    @CreatedDate
    private LocalDateTime createdAdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updateAdmin")
    private Admin updateAdmin;



    // alarm과 user_alarm는 1:N 관계
    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAlarm> userAlarms = new ArrayList<>();


}
