package com.example.amusetravelproejct.controller.admin.dto;

import lombok.*;

import java.util.Base64;
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
    private List<MainImageDto> mainImg;
    private List<TicketDto> ticket;
    private String mainInfo;
    private List<CourseDto> course;
    private String extraInfo;

    // Getter, Setter, Constructor 생략
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Location {
        private String country;
        private String city;

        // Getters and Setters
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class MainImageDto {
        private String fileName;
        private String base64Data;

        // Getter, Setter, Constructor 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class TicketDto {
        private String title;
        private String content;
        private List<PriceListDto> priceList;

        // Getter, Setter, Constructor 생략
        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class PriceListDto {
            private String startDate;
            private String endDate;
            private String price;

            private WeekdayPrices weekdayPrices;
            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class WeekdayPrices{
                private String Monday;
                private String Tuesday;
                private String Wednesday;
                private String Thursday;
                private String Friday;
            }

            // Getter, Setter, Constructor 생략
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class CourseDto {
        private String title;
        private String timeCost;
        private String content;
        private CourseImageDto image;

        // Getter, Setter, Constructor 생략
        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class CourseImageDto {
            private String fileName;
            private String base64Data;

            // Getter, Setter, Constructor 생략
        }
    }
}
