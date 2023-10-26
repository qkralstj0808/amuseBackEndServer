package com.example.amusetravelproejct.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity(name = "alarm")
@EntityListeners(value = {AuditingEntityListener.class})
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_admin")
    private Admin updateAdmin;

    // alarm과 user_alarm는 1:N 관계
    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAlarm> userAlarms = new ArrayList<>();
}
