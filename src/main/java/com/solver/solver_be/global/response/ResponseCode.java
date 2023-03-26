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

    // Visitor 관련
    VISITOR_WIRTE_SUCCESS(200, "방문 문서 작성 성공"),
    VISITOR_GET_SUCCESS(200, "방문 신청 내용 조회 성공"),
    VISITOR_UPDATE_SUCCESS(200, "방문 기록 수정 성공" ),
    VISITOR_DELETE_SUCCESS(200,"방문 기록 삭제 성공" ),

    /*============================ ORD ================================*/
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

    // MindMap 관련
    MINDMAP_UPLODAD_SUCCESS(200, "마인드 맵이 생성 되었습니다." ),
    MINDMAP_CHANGE_SUCCESS(200, "마인드 맵이 가져오는데 되었습니다." ),
    MINDMAP_UPDATE_SUCCESS(200, "마인드 맵 제목이 수정 되었습니다." ),
    MINDMAP_DELETE_SUCCESS(200, "마인드 맵이 삭제 되었습니다." ),

    // WorkSpace 관련

    WORKSPACE_UPLOAD_SUCCESS(200, "워크스페이스 생성 완료되었습니다."),
    WORKSPACE_GET_SUCCESS(200, "워크스페이스를 가져오는데 성공했습니다."),

    WORKSPACE_UPDATE_SUCCESS(200, "워크스페이스 업데이트 완료되었습니다."),
    WORKSPACE_DELETE_SUCCESS(200, "워크스페이스 삭제 완료되었습니다."),

    /*============================ FAIL ================================*/
//Global
    NOT_VALID_REQUEST(400, "유효하지 않은 요청입니다."),
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(400, "토큰을 찾을 수 없습니다."),
    NOT_VALID_REFRESH_TOKEN(400, "유효하지 않은 리프레시 토큰입니다."),

    //User 관련
    USER_ID_EXIST(400, "이미 존재하는 아이디입니다."),
    USER_NICKNAME_EXIST(400, "이미 존재하는 닉네임입니다."),
    USER_ACCOUNT_NOT_EXIST(400, "존재하지 않는 계정입니다."),
    USER_NOT_FOUND(400, "존재하지 않는 사용자입니다."),
    PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),

    //Visitor 관련
    VISITOR_NOT_FOUND(400,"방문 신청 기록이 없습니다." ),
    VISITOR_UPDATE_FAILED(400,"본인은 방문 기록이 아닙니다." ),

    //Image 관련
    IMAGE_NOT_FOUND(400, "존재하지 않는 이미지입니다."),

    // BOARD 관련
    BOARD_NOT_FOUND(400, "질문을 찾을 수 없습니다."),
    BOARD_UPDATE_FAILED(400, "질문 수정에 실패했습니다."),
    ANSWER_NOT_FOUND(400, "답변을 찾을 수 없습니다."),
    ANSWER_UPDATE_FAILED(400, "답변 수정에 실패했습니다."),

    // MindMap 관련
    MINDMAP_NOT_FOUND(400, "마인드 맵을 찾을 수 없습니다." ),
    MINDMAP_UPDATE_FAILED(400, "본인의 마인드 맵이 아닙니다." ),

    // WorkSpace 관련
    WORKSPACE_NOT_FOUND(400, "워크스페이스를 찾을 수 없습니다." ),
    INVALID_COMPANY_TOKEN(400, "유효하지 않은 회사 코드입니다." );

    private final int statusCode;
    private final String message;
}