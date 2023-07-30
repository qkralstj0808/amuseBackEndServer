package com.example.amusetravelproejct.dto.request;

import lombok.*;

public class AuthRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id_Password {
        private String id;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changePassword {
        private String id;
        private String password_for_change;
    }

}
