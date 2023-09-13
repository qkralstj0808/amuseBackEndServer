package com.example.amusetravelproejct.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmailRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Email {
        private String email;
    }
}
