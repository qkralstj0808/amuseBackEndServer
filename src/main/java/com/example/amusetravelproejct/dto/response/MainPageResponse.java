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
        private List<ItemInfo> bestItems;
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
        private String productId;
        private String category;
        private String imageUrl;
        private String title;
        private String country;
        private String city;
        private Long likeNum;
        private Long startPrice;
    }

}
