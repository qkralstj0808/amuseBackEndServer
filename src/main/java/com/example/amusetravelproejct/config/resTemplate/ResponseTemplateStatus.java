package com.example.amusetravelproejct.config.resTemplate;

import lombok.Getter;

@Getter
public enum ResponseTemplateStatus {

    //1000: 요청 성공 및 오류

    SUCCESS(true, "요청 성공", 1000),
    FAIL(false, "요청 실패", 1004),

    //2000: Request 성공 및 오류
    EMPTY_JWT(false, "JWT를 입력해주세요.", 2001),
    INVALID_JWT(false, "유효하지 않은 JWT입니다.", 2002),
    NO_JWT(false, "해당 JWT를 가진 사람이 없습니다.", 2003),

    //3000: DB 부분 오류
    DATABASE_ERROR(false, "데이버베이스 요청 오류", 3000),
    PATCH_DATABASE_ERROR(false, "닉네임 수정 데이터베이스 요청 오류", 3001),
    QUERY_ERROR(false,"쿼리 오류", 3002),


    //4000: 유저 부분 오류
    EMPTY_EMAIL(false, "이메일을 입력해주세요",4001),
    EMPTY_UPDATE(false, "변경할 사항이 없습니다.", 4002),
    EMPTY_PASSWORD(false, "비밀번호를 입력해주세요", 4003),
    EMPTY_NICKNAME(false, "닉네임을 입력해주세요,", 4004),
    USER_NOT_FOUND(false, "유저를 찾을 수 없습니다.", 4005),

    INVALID_OAUTH_ACCESS_TOKEN(false, "OAuth Accesstoken이 유효하지 않습니다.", 4006),
    INVALID_EMAIL(false, "이메일이 올바르지 않습니다.", 4007),
    INVALID_PASSWORD(false, "비밀번호가 올바르지 않습니다.", 4008),
    NO_AUTH_USER(false, "소셜로그인 유저는 비밀번호 변경이 불가능합니다.",4009),
    INVALID_SIGNUP(false, "회원가입 양식을 다시 한 번 확인해주세요", 4010),
    //양식
    INCORRECT_PASSWORD(false, "비밀번호 형식을 다시 한 번 확인해주세요.", 4011),
    NICKNAME_DUPLICATED(false, "이미 존재하는 닉네임입니다.", 4012),
    MODIFY_FAIL_NICKNAME(false,"닉네임을 바꿀 수 없습니다.", 4013),
    EMAIL_DUPLICATED(false, "이미 존재하는 이메일입니다.", 4014),
    PASSWORD_DUPLICATED(false, "비밀번호가 기존 비밀번호와 동일합니다.", 4015),
    EMPTY_WITHDRAWREASON(false, "탈퇴 사유를 입력해주세요.", 4016 ),
    INCORRECT_NICKNAME(false, "닉네임 형식을 다시 한 번 확인해주세요", 4017),
    FAIL_WITHDRAW(false, "탈퇴에 실패하였습니다.", 4018),
    MODIFY_FAIL_INTRODUCTION(false, "한줄소개 변경에 실패하였습니다.", 4019),
    ITEM_NOT_FOUND(false,"해당 상품은 존재하지 않습니다",4000),;


    private final boolean isSuccess;
    private final String message;
    private final int code;

    private ResponseTemplateStatus(boolean isSuccess, String message, int code){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
