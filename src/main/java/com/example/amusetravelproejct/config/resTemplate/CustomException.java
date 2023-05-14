package com.example.amusetravelproejct.config.resTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.rmi.ServerException;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;
}
