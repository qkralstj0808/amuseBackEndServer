package com.example.amusetravelproejct.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;

@Data
public class MainPageResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class getCategory {
        private List<CategoryInfo> categories;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CategoryInfo {
        private Long categoryId;
        private Long sequence;
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getCategoryPage {
        private Long category_id;
        private String name;
        private String imgUrl;
        private Long sequence;
        private String mainDescription;
        private String subDescription;
        private Integer pageCount;
        private List<?> pageComponentInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageBannerInfo {
        private Long page_component_id;
        private String type;
        private String title;
        private String PcBannerUrl;
        private String PcBannerLink;
        private String MobileBannerUrl;
        private String MobileBannerLink;
        private String content;
//        private List<ItemInfo> itemInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageListInfo {
        private Long page_component_id;
        private String type;
        private String title;
        private List<ItemInfo> itemInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageTileInfo {
        private Long page_component_id;
        private String type;
        private String title;
        private Integer tileCount;
        private List<TileInfo> tileList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TileInfo {
        private Long tile_id;
        private String tile_name;
        private String tile_images;
        private List<ItemInfo> itemInfos;
    }



}
