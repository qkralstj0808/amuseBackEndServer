package com.example.amusetravelproejct.oauth.handler;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.info("TokenAccessDeniedHandler 에서 handle 메서드 진입");
        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String errorMessage = "토근 권한이 없습니다.";
        PrintWriter writer = response.getWriter();
        writer.write("{\"error\": \"" + errorMessage + "\"}");
        writer.flush();
        writer.close();

        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
    }
}