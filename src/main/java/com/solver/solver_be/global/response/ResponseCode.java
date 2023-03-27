package com.solver.solver_be.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    /*============================ SUCCESS ================================*/

    // User 관련
    SIGN_UP_SUCCESS(200, "회원 가입이 완료되었습니다."),
    LOG_IN_SUCCESS(200, "로그인이 완료되었습니다."),

    // Visitor 관련
    VISITOR_WIRTE_SUCCESS(200, "방문 문서 작성 성공"),
    VISITOR_GET_SUCCESS(200, "방문 신청 내용 조회 성공"),
    VISITOR_UPDATE_SUCCESS(200, "방문 기록 수정 성공" ),
    VISITOR_DELETE_SUCCESS(200,"방문 기록 삭제 성공" ),

    // Token 관련
    TOKEN_UPDATE_SUCCESS(200, "토큰이 업데이트되었습니다."),

   // Company 관련
    COMPANY_REGISTER_SUCCESS(200, "회사 정보 등록을 완료했습니다." ),
    COMPANY_GET_SUCCESS(200, "등록된 회사 가져오기 성공했습니다. " ),
    COMPANY_UPDATE_SUCCESS(200, "회사 정보 변경을 완료했습니다."),
    COMPANY_DELETE_SUCCESS(200, "회사 정보 삭제를 완료했습니다."),

    /*============================ FAIL ================================*/
    //Global
    NOT_VALID_REQUEST(400, "유효하지 않은 요청입니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(400, "토큰을 찾을 수 없습니다."),
    NOT_VALID_REFRESH_TOKEN(400, "유효하지 않은 리프레시 토큰입니다."),

    // User 관련
    USER_ID_EXIST(400, "이미 존재하는 아이디입니다."),
    USER_NICKNAME_EXIST(400, "이미 존재하는 닉네임입니다."),
    USER_ACCOUNT_NOT_EXIST(400, "존재하지 않는 계정입니다."),
    USER_NOT_FOUND(400, "존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),
    INVALID_COMPANY_TOKEN(400, "유효하지 않은 회사 코드입니다." ),

    // Visitor 관련
    VISITOR_NOT_FOUND(400,"방문 신청 기록이 없습니다." ),
    VISITOR_UPDATE_FAILED(400,"본인은 방문 기록이 아닙니다." ),
    ADMIN_NOT_FOUND(400, "담당자가 없습니다."  ),

   // Company 관련
    COMPANY_ALREADY_EXIST(400, "등록된 회사가 이미 존재합니다."  ),
    COMPANY_NOT_FOUND(400, "회사를 찾을 수 없습니다."  );

    private final int statusCode;
    private final String message;
}