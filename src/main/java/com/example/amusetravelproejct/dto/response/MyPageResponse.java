package com.example.amusetravelproejct.dto.response;

import lombok.*;

import java.util.List;

public class MyPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getLikeItems {
        private List<ItemInfo> LikeItems;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemInfo {
        private Long item_db_id;
        private String product_code;
        private String imageUrl;
        private String title;
        private String country;
        private String city;
        private Integer duration;
        private Integer likeNum;
        private Long startPrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class setReview {
        private String user_name;
        private Float rating;
        private String review_content;
        private List<ImageInfo> images;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getReview {
        private List<ReviewInfo> reviewInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewInfo {
        private Long item_id;
        private String user_name;
        private Float rating;
        private String review_content;
        private List<ImageInfo> images;
    }




    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageInfo {
        private String imgUrl;
    }
}
