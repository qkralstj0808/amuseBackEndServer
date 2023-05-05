package com.example.amusetravelproejct.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;

@Data
public class MainPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getCategory {
        private List<CategoryInfo> categories;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryInfo {
        private Long categoryId;
        private String categoryName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getItem {
        private List<ItemInfo> items;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getBestItemPage {
        private List<ItemInfo> bestItems;
        private Integer totalPage;
        private Integer currentPage;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getCurrentItemPage {
        private List<ItemInfo> currentItems;
        private Integer totalPage;
        private Integer currentPage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getBestItem {
        private List<ItemInfo> bestItems;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getCurrentItem {
        private List<ItemInfo> currentItems;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemInfo {
        private Long item_db_id;
        private String product_code;
        private String category;
        private String imageUrl;
        private String title;
        private String country;
        private String city;
        private Integer duration;
        private Integer likeNum;
        private Long startPrice;
    }

}
