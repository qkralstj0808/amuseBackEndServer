package com.example.amusetravelproejct.dto.request;

import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;

import javax.swing.*;
import java.util.List;

public class MainPageRequest {

    @Data
    public static class getListItem{
        private mainPageComponentListInfo mainPageComponentListInfo;
        private List<Item> item;

        @QueryProjection
        public getListItem(mainPageComponentListInfo mainPageComponentListInfo, List<Item> item){
            this.mainPageComponentListInfo = mainPageComponentListInfo;
            this.item = item;
        }

    }

    @Data
    public static class mainPageComponentListInfo{
        private String title;
        private Long sequence;

        @QueryProjection
        public mainPageComponentListInfo(String title,Long sequence){
            this.title = title;
            this.sequence = sequence;
        }
    }

    @Data
    public static class ItemInfo {
        private Item item;
//        private Long item_db_id;
//        private String product_code;
//        private List<HashTag> hashTags;
//        private List<ImageUrl> itemImgs;
//        private String title;
//        private String country;
//        private String city;
//        private Integer duration;
//        private Integer likeNum;
//        private Long startPrice;
//
//        @QueryProjection
//        public ItemInfo(Long item_db_id, String product_code, List<HashTag> itemHashTags, List<ImageUrl> imageUrls, String title, String country, String city, Integer duration, Integer likeNum, Long startPrice) {
//            this.item_db_id = item_db_id;
//            this.product_code = product_code;
//            this.hashTags = itemHashTags;
//            this.itemImgs = imageUrls;
//            this.title = title;
//            this.country = country;
//            this.city = city;
//            this.duration = duration;
//            this.likeNum = likeNum;
//            this.startPrice = startPrice;
//        }

        @QueryProjection
        public ItemInfo(Item item){
            this.item = item;
        }

    }


    @Data
    public static class ImageUrl {
        private String imaUrl;

        @QueryProjection
        public ImageUrl(String imaUrl) {
            this.imaUrl = imaUrl;
        }
    }

    @Data
    public static class HashTag {
        private String hashtag;

        @QueryProjection
        public HashTag(String hashtag) {
            this.hashtag = hashtag;
        }
    }
}
