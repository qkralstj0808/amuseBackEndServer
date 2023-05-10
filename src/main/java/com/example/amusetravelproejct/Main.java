package com.example.amusetravelproejct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@EnableJpaAuditing
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> {
            p.setOneIndexedParameters(true);	// 1부터 시작
            p.setMaxPageSize(2);				// size=10
        };
    }
}