package com.example.amusetravelproejct.dto.request;

import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.domain.person_enum.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductRegisterDto {
    private Long id;
    private Option option;
    private String itemCode;
    private List<String> category;
    private String title;
    private Location location;
    private List<MainImageDto> mainImg;
    private List<TicketDto> ticket;
    private String mainInfo;
    private List<CourseDto> course;
    private String extraInfo;
    private String admin;
    private String updateAdmin;
    private Long startPrice;
    private String duration;
    private String startDate;
    private String endDate;
    private Grade grade;

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
        private Long id;
        private String fileName;
        private String base64Data;
        private String imgUrl;
        // Getter, Setter, Constructor 생략
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class TicketDto {
        private Long id;
        private String title;
        private String content;
        private List<PriceListDto> priceList;

        // Getter, Setter, Constructor 생략
        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class PriceListDto {
            private Long id;
            private String startDate;
            private String endDate;
            private WeekdayPrices weekdayPrices;
            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class WeekdayPrices{
                private String mon;
                private String tue;
                private String wed;
                private String thu;
                private String fri;
                private String sat;
                private String sun;
            }

            // Getter, Setter, Constructor 생략
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class CourseDto {
        private Long id;
        private Long sequenceId;
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
            private String imgUrl;


            // Getter, Setter, Constructor 생략
        }
    }
}
