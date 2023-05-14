package com.example.amusetravelproejct.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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



}
