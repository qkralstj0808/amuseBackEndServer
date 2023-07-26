package com.example.amusetravelproejct.oauth.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 비밀번호 인증 실패 시에 사용자 정의 에러 처리를 수행합니다.
        // 원하는 에러 메시지를 설정하고 원하는 URL로 리다이렉션할 수 있습니다.

        String errorMessage = "로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.";
        String loginPageUrl = "/login?error=true&message=" + errorMessage;
        setDefaultFailureUrl(loginPageUrl);

        super.onAuthenticationFailure(request, response, exception);
    }
}
