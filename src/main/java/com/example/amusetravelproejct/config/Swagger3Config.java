package com.example.amusetravelproejct.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

// SwaggerConfig.java
@OpenAPIDefinition(
        info = @Info(title = "어뮤즈트레블 API 명세서",
                description = "어뮤즈트레블 API 명세서입니다",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class Swagger3Config {



    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("어뮤즈트레블 v1")
                .pathsToMatch(paths)
                .build();
    }




}
