package com.example.amusetravelproejct.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItemResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getAllItemId{
        private List<Long> item_ids;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getAllDisplayItem {
        private List<ItemInfo> items;
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
    public static class HashTag {
        private String hashtag;
    }

}
