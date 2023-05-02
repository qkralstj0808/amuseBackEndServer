package com.example.amusetravelproejct.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class DetailPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getTitle {
        private String country;
        private String city;
        private String title;
        private float rated;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getIcon {
        private List<IconInfo> icon_infos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IconInfo {
        private String icon;
        private String text;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getPicture {
        private List<String> pictures;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getTicket {
        private List<TicketInfo> ticketList;
        private List<ProductPrice> priceList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TicketInfo {
        private String title;
        private String content;
        private String price;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductPrice {
        private String startDate;
        private String endDate;
        private String price;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getContent {
        private List<String> content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getCourseContent {
        private List<CourseInfo> course;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CourseInfo {
        private String title;
        private String content;
        private Long sequenceId;
        private Long timeCost;
        private String imageUrl;
        private Double latitude;
        private Double longitude;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getOtherContent {
        private List<String> content;
    }


//    리뷰, 담당자는 나중에



}
