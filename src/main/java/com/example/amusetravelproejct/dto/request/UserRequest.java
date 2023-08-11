package com.example.amusetravelproejct.dto.request;

import com.example.amusetravelproejct.domain.person_enum.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @Builder
    public static class createUserInfoBeforeLogin {
        private String phone_number;

        @Builder.Default()
        private Boolean advertisement_true = false;

        @Builder.Default()
        private Boolean over_14_age_true = false;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changeUserGrade {
        private Grade grade;
    }
}
