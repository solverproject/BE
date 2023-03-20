package com.solver.solver_be.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /*============================ SUCCESS ================================*/

    //User 관련
    SIGN_UP_SUCCESS(200, "회원 가입이 완료되었습니다."),
    LOG_IN_SUCCESS(200, "로그인이 완료되었습니다."),

    //Board 관련
    BOARD_UPLOAD_SUCCESS(200, "질문 등록이 완료되었습니다."),
    BOARD_LIST_GET_SUCCESS(200, "질문 리스트를 가져오는데 성공했습니다."),
    BOARD_GET_SUCCESS(200, "질문을 가져오는데 성공했습니다."),
    BOARD_UPDATE_SUCCESS(200, "질문 수정이 완료되었습니다."),
    BOARD_DELETE_SUCCESS(200, "질문 삭제가 완료되었습니다."),
    FAVORITE_SUCCESS(200, "즐겨찾기가 추가되었습니다."),

    ANSWER_UPLOAD_SUCCESS(200, "답변 등록이 완료되었습니다."),
    ANSWER_UPDATE_SUCCESS(200, "답변 수정이 완료되었습니다."),
    ANSWER_DELETE_SUCCESS(200, "답변 삭제가 완료되었습니다."),

    // Follow 관련
    FOLLOW_SUCCESS(200, "팔로우가 완료되었습니다."),
    FOLLOW_CANCEL(200, "팔로우가 취소되었습니다."),

    // Token 관련
    TOKEN_UPDATE_SUCCESS(200, "토큰이 업데이트되었습니다."),

    /*============================ FAIL ================================*/
//Global
    NOT_VALID_REQUEST(400, "유효하지 않은 요청입니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(400, "토큰을 찾을 수 없습니다."),
    NOT_VALID_REFRESH_TOKEN(400, "유효하지 않은 리프레시 토큰입니다."),

    //User 관련
    USER_EMAIL_EXIST(400, "이미 존재하는 이메일입니다."),
    USER_NICKNAME_EXIST(400, "이미 존재하는 닉네임입니다."),
    USER_ACCOUNT_NOT_EXIST(400, "존재하지 않는 계정입니다."),
    USER_NOT_FOUND(400, "존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),

    //Image 관련
    IMAGE_NOT_FOUND(400, "존재하지 않는 이미지입니다."),

    // BOARD 관련
    BOARD_NOT_FOUND(400, "질문을 찾을 수 없습니다."),
    BOARD_UPDATE_FAILED(400, "질문 수정에 실패했습니다."),
    ANSWER_NOT_FOUND(400, "답변을 찾을 수 없습니다."),
    ANSWER_UPDATE_FAILED(400, "답변 수정에 실패했습니다.");

    private final int statusCode;
    private final String message;
}