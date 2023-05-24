package com.example.amusetravelproejct.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DetailPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getTitle {
        private String itemCode;
        private String country;
        private String city;
        private String title;
        private Double rated;
        private Integer review_count;
        private Integer duration;
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
        private List<TicketInfo> ticket;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TicketInfo {
        private Long id;
        private String title;
        private String content;
        private List<TicketPrice> priceList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TicketPrice {
        private String startDate;
        private Long price;
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
        private String content;
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
        private Integer day;
        private Long sequenceId;
        private String timeCost;
        private String imageUrl;
        private Double latitude;
        private Double longitude;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getOtherContent {
        private String content;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class setLike {
        private Integer like_num;
    }


//    리뷰, 담당자는 나중에

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getManager {
        private String title;
        private String name;
        private String email;
        private String img;
        private String manager_content;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getReview {
        private Double rated;
        private Integer review_count;
        private List<ReviewImage> review_all_imgs;
        private List<ReviewInfo> reviews;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewInfo{
        private String user_name;
        private String review_content;
        private Float user_rate;
        private LocalDateTime create_date;
        private List<ReviewImage> images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewImage{
        private String review_img;
    }




}
