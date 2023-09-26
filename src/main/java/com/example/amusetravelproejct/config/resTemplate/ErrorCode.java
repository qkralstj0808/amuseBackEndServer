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
    NOT_ALLOWED_ACCESS_TOKEN(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,"해당 api는 관리자만 사용할 수 있습니다 해당 access token은 관리자 " +
            "권한이 없습니다"),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 상품은 존재하지 않습니다"),
    LIKE_UNDER_0(HttpStatus.NOT_FOUND,"좋아요 수는 0 이상이여야 합니다."),
    EXIT_LIKE_ITEM(HttpStatus.MULTI_STATUS,"좋아요를 이미 누른 상품입니다."),
    NOT_EXIT_LIKE_ITEM(HttpStatus.NOT_FOUND,"좋아요를 누른 상품이 아닙니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 유저를 찾을 수 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"access token이 만료되었습니다"),
    NOT_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED,"access token이 만료되지 않았습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"유효하지 않는 access token 입니다."),
    CLAIM_NULL(HttpStatus.NOT_EXTENDED,"user claim이 존재하지 않습니다."),
//    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 관리자를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 access token에 대한 refresh token이 존재하지 않습니다."),
    HASH_TAG_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 해시태그를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"인터넷 내부 에러입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 카테고리를 찾을 수 없습니다."),
    ITEM_HASH_TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 해시태그를 찾을 수 없습니다"),
    ADVERTISEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 광고를 찾을 수 없습니다."),
    ITEM_CODE_DUPLICATION(HttpStatus.BAD_REQUEST,"상품 코드가 중복됩니다."),
    MAIN_PAGE_COMPONENT_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 컴포넌트가 없습니다."),
    SEQUENCE_ERROR_SET(HttpStatus.BAD_REQUEST,"해당 순서는 잘못된 설정입니다." ),
    NOT_FOUND_GUIDE(HttpStatus.NOT_FOUND,"해당 가이드는 찾을 수 없습니다."),
    ITEM_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"해당 상품코드는 이미 존재 합니다"),
    PAGE_COMPONENT_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 컴포넌트는 존재 하지 않습니다."),

    RESERVATION_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 추가 예약 정보 메시지를 찾을 수 없습니다." ),

    CANCEL_POLICY_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 취소 정책 메시지를 찾을 수 없습니다." ),

    PAYMENT_METHOD_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 결제 방법 메시지를 찾을 수 없습니다." ),

    TERMS_OF_SERVICE_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 약관동의 메시지를 찾을 수 없습니다." ),
    // paging 관련 오류
    OUT_BOUND_PAGE(HttpStatus.CONFLICT, "페이지 범위를 벗어났습니다. 페이지는 1페이지부터 totalPage까지 입니다."),
    ITEM_NOT_FOUND_IN_PAGE(HttpStatus.NOT_FOUND,"조건에 맞는 상품이 없습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 상품에 매니저를 찾을 수 없습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"잘못된 요청입니다."),
    ASC_DESC_WORD_ERROR(HttpStatus.BAD_REQUEST,"asc_desc 칸에는 '오른차순' 또는 '내림차순' 둘 중 하나로 적어주세요"),
    SORT_FUNCTION_ERROR(HttpStatus.BAD_REQUEST,"sort 함수를 만드는 과정에서 Null이 발생했습니다."),
    CATEGORY_EXIT(HttpStatus.BAD_REQUEST,"이미 해당 이름의 category가 존재합니다. 수정을 원하면 PUT 해주세요" ),
    ID_EXIST(HttpStatus.FOUND,"아이디가 존재합니다"),
    ID_NOT_EXIST(HttpStatus.FOUND,"아이디가 존재하지 않습니다"),
    PASSWORD_INCORRECT(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS,"비밀번호가 일치하지 않습니다"),
    EMPTY(HttpStatus.NO_CONTENT,"값이 비었습니다"),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND,"이미지를 찾지 못했습니다."),
    COURSE_NOT_FOUND( HttpStatus.NOT_FOUND,"코스를 찾을 수 없습니다."),
    UNLINKFAIL(HttpStatus.FAILED_DEPENDENCY,"소셜로그인과 연동 해제에 실패했습니다." ),
    OAUTHORIZEDCLIENT_NULL(HttpStatus.NOT_FOUND,"oauthorizedclient가 null이다." );


    private final HttpStatus httpStatus;
    private final String message;
}
