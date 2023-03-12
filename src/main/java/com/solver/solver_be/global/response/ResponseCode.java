package com.solver.solver_be.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /*============================ SUCCESS ================================*/

    //User 관련
    SIGN_UP_SUCCESS(200, "회원 가입에 성공했습니다"),
    LOG_IN_SUCCESS(200, "로그인되었습니다."),

    //Board 관련
    BOARD_UPLOAD_SUCCESS(200, "질문등록이 완료되었습니다."),
    BOARD_LIST_GET_SUCCESS(200, "질문리스트 가져오기 완료했습니다."),
    BOARD_GET_SUCCESS(200, "질문리스트 가져오기 완료했습니다."),
    BOARD_UPDATE_SUCCESS(200, "질문 업데이트 완료되었습니다." ),
    BOARD_DELETE_SUCCESS(200, "질문 삭제 완료되었습니다." ),

    /*============================ FAIL ================================*/

    //Global
    NOT_VALID_REQUEST(400,  "유효하지 않은 요청입니다."),
    NOT_VALID_TOKEN(400,"유효한 토큰이 아닙니다."),
    TOKEN_NOT_FOUND(400,  "토큰이 없습니다."),

    //User 관련
    USER_EMAIL_EXIST(400, "이미 존재하는 이메일입니다."),
    USER_NICKNAME_EXIST(400, "이미 존재하는 닉네임입니다."),
    USER_ACCOUNT_NOT_EXIST(400,  "계정 정보가 존재하지 않습니다."),
    USER_NOT_FOUND(400, "사용자가 존재하지 않습니다."),
    PASSWORD_MISMATCH(400,  "비밀번호가 일치하지 않습니다."),

    //Image 관련
    IMAGE_NOT_FOUND(400, "존재하지 않는 이미지입니다."),

    // BOARD 관련
    BOARD_NOT_FOUND(400, "질문을 찾지 못했습니다."),
    BOARD_UPDATE_FAILED(400, "질문 업데이트에 실패하였습니다.");

    private final int statusCode;
    private final String message;
}