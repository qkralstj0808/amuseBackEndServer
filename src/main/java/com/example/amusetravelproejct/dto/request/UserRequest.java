package com.example.amusetravelproejct.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class createUserInfo {
        private String phone_number;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getUserInfoBeforeLogin {
        private String phone_number;
        private Boolean advertisement_true;
        private Boolean over_14_age_true;
    }
}
