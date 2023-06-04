package com.example.amusetravelproejct.dto.request;

import com.example.amusetravelproejct.oauth.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateUserRoleType{
        private RoleType roleType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class guide{
        private String name;
        private String email;
        private String guideCode;
        private String introduce;
        private String fileName;
        private String base64Data;
        private String imgUrl;
    }


}
