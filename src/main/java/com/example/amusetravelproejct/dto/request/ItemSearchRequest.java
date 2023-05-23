package com.example.amusetravelproejct.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ItemSearchRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemSearchKeywordDto {
        private String contain_word;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemConditionDto {
        private String city;
        private String country;
        private Integer duration;
        private List<SortCondition> sortConditions;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemSearchConditionDto {
        private String contain_word;
        private List<SortCondition> sortConditions;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortCondition{
        private Boolean rated;
        private Boolean like_num;
        private Boolean startPrice;
        private Boolean date;
        private String asc_or_desc;
    }


}
