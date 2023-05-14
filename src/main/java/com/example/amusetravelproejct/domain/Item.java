package com.example.amusetravelproejct.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "item")
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // db의 고유 Id
    private String itemCode;          // 상품 코드
    private String country;         // 나라
    private String city;            // 시 (강원도 까지만 하기로 했습니다!)
    private String title;           // 상품 제목
    private String content_1;       // html을 String으로 바꾼 내용 1번째
    private String content_2;       // html을 String으로 바꾼 내용 2번째 취소 내용

    private Double rated;            // 모든 리뷰들 평점의 평균
    private Long startPrice;     // 많은 상품 가격 중 가장 싼 것
    private Integer duration;          // 기간 (2박 3일 에서 3)
    private Integer like_num;          // 좋아요 수
    private Date startDate;
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_admin")
    private Admin updateAdmin;

    // item와 category는 N:1 관계 ManyToOne

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> category_list = new ArrayList<>();

    // item와 ItemImg 1:N 관계
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImg> itemImg_list = new ArrayList<>();

    // item에는 여러개 아이콘 가능
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemIcon> itemIcon_list = new ArrayList<>();

    // item에는 여러개 티켓이 있다.
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTicket> itemTickets = new ArrayList<>();

    // item와 item_course는 1:N 관계
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCourse> itemCourses = new ArrayList<>();

}
