package com.example.amusetravelproejct.controller.admin.dto.resp;

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
        private Date startDate;
        private Date endDate;
        private String adType;
        private String adCategory;
        private String adContent;
        private LocalDateTime createdAdDate;
        private String createdAd;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementEdit {
        private Long id;
        private String title;
        private Date startDate;
        private Date endDate;
        private String adType;
        private String adCategory;
        private String adContent;
        private LocalDateTime updatedAdDate;
        private String updatedAd;
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
        private LocalDateTime updatedAdDate;
        private String updatedAd;
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





}
