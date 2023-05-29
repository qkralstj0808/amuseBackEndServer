package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class AdminResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getUser{
        private List<UserInfo> userInfos;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateUserRoleType{
        private UserInfo userInfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo{
        private Long user_db_id;
        private String userId;
        private String userName;
        private String email;
        private String profileImageUrl;
        private ProviderType providerType;
        private RoleType roleType;
    }

}
