package com.example.amusetravelproejct.controller.admin.dto.resp;

import lombok.*;

import javax.xml.stream.Location;
import java.awt.*;
import java.util.ArrayList;

public class ItemPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class itemRegister {
        private String productId;
        private String category;
        private String title;
        private Location location;
        private ArrayList<Image> mainImg;
        private ArrayList<Ticket> ticket;
        private String mainInfo;
        private ArrayList<Course> course;
        private String extraInfo;
    }
    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    public class Image {
        private String imageURL;

    }
    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    public class Location {
        private String country;
        private String city;

        //생성자, Getter, Setter 생략
    }

    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    public class Ticket {
        private String title;
        private String content;
        private ArrayList<Price> priceList;

        //생성자, Getter, Setter 생략
    }
    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    public class Price {
        private String startDate;
        private String endDate;
        private String price;

        //생성자, Getter, Setter 생략
    }
    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    public class Course {
        private String title;
        private String timeCost;
        private String content;
        private String imageURL;

        //생성자, Getter, Setter 생략
    }
}
