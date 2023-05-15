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
        private String[] adCategory;
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
        private String[] adCategory;
        private String adContent;
        private String createdBy;
        private String createdAt;
        private String updatedAt;
        private String updatedBy;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementResult{
        private int pageCount;
        private int page;
        private List<advertisementList> data;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class advertisementList{
        private Long id;
        private String title;
        private Date startDate;
        private Date endDate;
        private String[] adCategory;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
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
    public static class noticeResult{
        private int pageCount;
        private int page;
        private List<noticeList> data;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class noticeList{
        private Long id;
        private String title;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryRegister {
        private List<categoryDetail> displayHashTags;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryDetail{
        private Long id;
        private String displayHashTag;
        private Long sequence;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
    }

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class findItemByCategoryResult{
//        private List<findItemByCategory> Items;
//    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class findItemByCategory{
        private Long id;
        private String title;
        private String content;
        private String[] hashTags;
        private String createdBy;
        private LocalDateTime createdAt;
        private String updatedBy;
        private LocalDateTime updatedAt;
    }

}
