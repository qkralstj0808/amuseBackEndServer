package com.example.amusetravelproejct.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminPageRequest {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementRegister {
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerFileName;
        private String pcBannerBase64;
        private String pcBannerLink;
        private String mobileBannerFileName;
        private String mobileBannerBase64;
        private String mobileBannerLink;
        private String adCategory;
        private String adContent;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementEdit {
        private Long id;
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerFileName;
        private String pcBannerBase64;
        private String pcBannerLink;
        private String mobileBannerFileName;
        private String mobileBannerBase64;
        private String mobileBannerLink;
        private String adCategory;
        private String adContent;
        private String createdBy;        private String updatedBy;
        private String pcBannerUrl;
        private String mobileBannerUrl;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryRegister {
        private String categoryName;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryDetail {
        private Long id;
        private Long offset;
        private Long limit;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeRegister {
        private String title;
        private String content;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeEdit{
        private Long id;
        private String title;
        private String content;
        private String updatedBy;
    }
}


