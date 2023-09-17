package com.example.amusetravelproejct.dto.response;

import com.example.amusetravelproejct.domain.person_enum.Gender;
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
    public static class getAccessToken_targetUrl {
//        private String targetUrl;
        private String accessToken;
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changePassword {
        private String massage;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class emailAuth {
        private String email;
        private String name;
        private Integer birthday;
        private Gender gender; //MAN or WOMAN
    }
}
