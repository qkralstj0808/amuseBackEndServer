package com.example.amusetravelproejct.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class AdminPageResponse {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementRegister {
        private Long id;
        private String title;
        private String startDate;
        private String endDate;
        private String pcBannerUrl;
        private String pcBannerLink;
        private String mobileBannerUrl;
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
        private String pcBannerUrl;
        private String pcBannerLink;
        private String mobileBannerUrl;
        private String mobileBannerLink;
        private String adCategory;
        private String adContent;
        private String createdBy;
        private String createdAt;
        private String updatedAt;
        private String updatedBy;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementList{
        private Long id;
        private String title;
        private Date startDate;
        private Date endDate;
        private String adType;
        private String adCategory;
        private String adContent;
        private LocalDateTime createdAdDate;
        private String createdAd;
        private LocalDateTime updatedAdDate;
        private String updatedAd;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryRegister {
        private Long id;
        private String categoryName;
        private LocalDateTime createAt;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryDetailList{
        private String category;
        private List<categoryDetail> data;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryDetail{
        private Long id;
        private String code;
        private String title;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class category{
        private Long id;
        private String categoryName;
        private LocalDateTime createdAt;
        private String createdBy;
        private Long item;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeRegister {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private String createdBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeEdit {
        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeList{
        private Long noticeCount;
        private List<noticeEdit> data;
    }


}
