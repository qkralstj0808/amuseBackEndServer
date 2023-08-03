package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.domain.person_enum.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getUserInfo {
        private String id;
        private String name;
        private String img_url;
        private String email;
        private Grade grade;
        private String phone_number;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getUserInfoBeforeLogin{
        private String id;
        private String name;
        private String img_url;
        private String email;
        private Grade grade;
        private String phone_number;
        private Boolean advertisementTrue;
        private Boolean over14AgeTrue;
    }
}
