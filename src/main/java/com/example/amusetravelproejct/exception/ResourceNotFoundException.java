package com.example.amusetravelproejct.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceName;

    public ResourceNotFoundException(String resourceName){
        this.resourceName = resourceName;
    }
}
