package com.example.amusetravelproejct.config.resTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseException extends RuntimeException{ //사용자정의 예외 던지기 위함
    private final Boolean isSuccess;
    private final String message; //메시지 전달
    private final int code; //내부 코드

    public ResponseException(ResponseTemplateStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();

    }
}