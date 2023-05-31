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
        private String categoryImg;
        private String mainDescription;
        private String subDescription;

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
    public static class getItemPage {
        private List<ItemInfo> Items;
        private Integer totalPage;
        private Integer currentPage;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getListItem {
        private Integer list_count;
        private List<ListItem> listItems;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ListItem {
        private String list_title;
        private Long sequence;
        private Integer item_count;
        private List<ItemInfo> items;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemInfo {
        private Long item_db_id;
        private String product_code;
        private List<HashTag> hashTags;
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
