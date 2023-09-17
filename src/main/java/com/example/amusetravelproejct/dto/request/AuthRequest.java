package com.example.amusetravelproejct.dto.request;

import com.example.amusetravelproejct.domain.person_enum.Gender;
import lombok.*;

import java.time.LocalDate;

public class AuthRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class adminSignUp {
        private String id;
        private String password;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class userSignUp {
        private String email;
        private String name;
        private Gender gender; //MAN or WOMAN
        private String birthday;
        private String phoneNumber;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changePassword {
        private String id;
        private String password_for_change;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class emailAuth {
        private String name;
        private Integer birthday;
        private String email;
        private Gender gender; //MAN or WOMAN
    }
}
