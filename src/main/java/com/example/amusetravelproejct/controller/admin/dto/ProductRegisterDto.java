package com.example.amusetravelproejct.controller.admin.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductRegisterDto {
    private String productId;
    private String category;
    private String title;
    private Location location;
    private List<Image> mainImg;
    private List<Ticket> ticket;
    private String productInfo;
    private List<Course> course;
    private String extraInfo;

    @Getter
    @Setter
    @NoArgsConstructor
    static public class Location {
        private String country;
        private String city;

        //생성자, Getter, Setter 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static public class Image {
        private String imageURL;

        //생성자, Getter, Setter 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static public class Ticket {
        private String title;
        private String content;
        private List<Price> priceList;

        //생성자, Getter, Setter 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static public class Price {
        private Date startDate;
        private Date endDate;
        private String price;

        //생성자, Getter, Setter 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static public class Course {
        private String title;
        private String timeCost;
        private String content;
        private String imageURL;

        //생성자, Getter, Setter 생략
    }
}
