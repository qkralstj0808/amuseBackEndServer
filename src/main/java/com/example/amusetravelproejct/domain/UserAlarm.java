package com.example.amusetravelproejct.domain;



import lombok.Getter;

import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;

@Entity(name = "user_alarm")
@Getter
@Setter
public class UserAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean status;

    // user_alarm과 user는 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // user_alarm과 alarm는 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;









}
