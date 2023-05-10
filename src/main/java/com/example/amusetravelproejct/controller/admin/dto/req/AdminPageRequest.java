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
}
