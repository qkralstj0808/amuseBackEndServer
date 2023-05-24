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
        private Double rating;
        private String review_content;
        private List<ImageInfo> images;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class ImageInfo {
        private Long id;
        private String fileName;
        private String base64Data;
        private String imgUrl;
    }
}
