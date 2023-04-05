package com.example.amusetravelproejct.domain;

import lombok.Getter;
import lombok.Setter;
import com.example.amusetravelproejct.domain.person_enum.DomesticOverseas;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "iteminfo")
@Getter
@Setter
public class Iteminfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemCode;
    private String itemTitle;
    private Long startingPrice;
    private Long maxPrice;
    private String province;
    private DomesticOverseas domesticOverseas;
    private String cityInProvince;
    private Long duration;
    private String notIncluded;
    private String included;
    private Long good; // (수정) like는 예약어라서 good으로 변경
    private String itemIntroduction;
    private Long usageTime;
    private String locationGuide;
    private Double Latitude;
    private Double Longitude;
    private String usageMethod;
    private String location;
    private Double mapTopLeftLatitude;
    private Double mapTopLeftLongitude;
    private Double mapBottomRightLatitude;
    private Double mapBottomRightLongitude;

    // iteminfo와 category는 N:1 관계 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // iteminfo와 item_option은 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOption> itemOptions = new ArrayList<>();

    // iteminfo와 item_course는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCourse> itemCourses = new ArrayList<>();

    // iteminfo와 item_ticket과 는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTicket> itemTickets = new ArrayList<>();

    // iteminfo와 estimate_contact는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstimateContact> estimateContacts = new ArrayList<>();

    // iteminfo와 item_price는 N:1 관계(수정)
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPrice> itemPrices = new ArrayList<>();

    // iteminfo와 item_add_option는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemAddOption> itemAddOptions = new ArrayList<>();

    // iteminfo와 img는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Img> imgs = new ArrayList<>();

    // iteminfo와 item_estimation는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEstimation> itemEstimations = new ArrayList<>();

    // iteminfo와 order_item는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // iteminfo와 like_item는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeItem> likeItems = new ArrayList<>();

    // iteminfo와 paymentInfo는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentInfo> paymentInfos = new ArrayList<>();

    // iteminfo와 supervisor_info는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupervisorInfo> supervisorInfos = new ArrayList<>();

    // iteminfo와 item_introduction_image는 1:N 관계
    @OneToMany(mappedBy = "iteminfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemIntroductionImage> itemIntroductionImages = new ArrayList<>();


}
