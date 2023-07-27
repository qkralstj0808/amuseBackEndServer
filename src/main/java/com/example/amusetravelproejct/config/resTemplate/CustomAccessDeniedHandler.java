//package com.example.amusetravelproejct.config.resTemplate;
//
//import com.example.amusetravelproejct.oauth.filter.TokenAuthenticationFilter;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
////
////    @Override
////    public void handle(HttpServletRequest request,
////                       HttpServletResponse response,
////                       AccessDeniedException ex) throws IOException, ServletException {
////
////        ErrorResponseEntity errorResponse = ErrorResponseEntity.toResponseEntity(ErrorCode.NOT_ALLOWED_ACCESS_TOKEN).getBody();
////        response.setStatus(HttpStatus.UNAUTHORIZED.value());
////        response.setContentType("application/json");
////        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
////    }
//
//
//
//
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public void handle(HttpServletRequest request,
//                       HttpServletResponse response,
//                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
//
//        // 토큰이 유효한지 확인
//        TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
//        try {
//            tokenFilter.validateToken(request); // 예외 발생 가능
//        } catch (AuthenticationException e) {
//            // 토큰이 유효하지 않으면 TokenAccessDeniedHandler를 호출하여 처리
//            TokenAccessDeniedHandler tokenAccessDeniedHandler = new TokenAccessDeniedHandler();
//            tokenAccessDeniedHandler.handle(request, response, accessDeniedException);
//            return;
//        }
//
//        // 토큰이 유효하면 CustomAccessDeniedHandler에서 처리
//        ErrorResponseEntity errorResponse = ErrorResponseEntity.toResponseEntity(ErrorCode.NOT_ALLOWED_ACCESS_TOKEN);
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setContentType("application/json");
//        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
//    }
//}
