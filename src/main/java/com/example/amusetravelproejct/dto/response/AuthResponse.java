package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.oauth.entity.ProviderType;
import com.example.amusetravelproejct.oauth.entity.RoleType;
import com.example.amusetravelproejct.oauth.token.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getNewAccessToken {
        private AuthToken newAccessToken;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getAccessToken {
        private String accessToken;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getError {
        private String accessToken;
    }
}
