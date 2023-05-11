package com.example.amusetravelproejct.controller.admin.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminPageRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class categoryRegister {
        private String categoryName;
        private String createdAd;
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


