package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.social.oauth.token.AuthToken;
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
}
