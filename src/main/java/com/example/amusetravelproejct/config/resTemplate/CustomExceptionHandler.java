package com.example.amusetravelproejct.config.resTemplate;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.amusetravelproejct.config.resTemplate.CustomException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseEntity> handleBadCredentialsException(BadCredentialsException ex) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setTimestamp(LocalDateTime.now());
//        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//        errorResponse.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
//        errorResponse.setMessage("로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");

        return ErrorResponseEntity.toResponseEntity(ErrorCode.PASSWORD_INCORRECT);
    }
}