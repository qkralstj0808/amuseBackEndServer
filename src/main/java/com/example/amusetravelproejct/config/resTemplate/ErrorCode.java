package com.example.amusetravelproejct.config.resTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 상품은 존재하지 않습니다")
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
