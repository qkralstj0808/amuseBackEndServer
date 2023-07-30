package com.example.amusetravelproejct.config.resTemplate;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.amusetravelproejct.config.resTemplate.CustomException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
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

        return ErrorResponseEntity.toResponseEntity(ErrorCode.PASSWORD_INCORRECT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseEntity> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ErrorResponseEntity.toResponseEntity(ErrorCode.NOT_ALLOWED_ACCESS_TOKEN);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE) // 우선순위 설정
    public ResponseEntity<ErrorResponseEntity> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return ErrorResponseEntity.toResponseEntity(ErrorCode.NOT_ALLOWED_ACCESS_TOKEN);
    }
}