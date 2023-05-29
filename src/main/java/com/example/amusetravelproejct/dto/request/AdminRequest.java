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
}
