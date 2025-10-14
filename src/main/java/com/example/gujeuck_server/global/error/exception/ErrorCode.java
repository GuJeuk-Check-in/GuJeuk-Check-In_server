package com.example.gujeuck_server.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    //jwt
    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "검증 되지 않은 토큰 입니다."),
    REFRESH_TOKEN_NOT_FOUND(404, "일치 하는 RefreshToken이 존재 하지 않습니다."),
    INVALID_ROLE(401,"유효 하지 않은 역할입니다."),

    //log
    LOG_NOT_FOUND(404, "존재하지 않는 이용목록입니다."),

    //excel
    EXCEL_GENERATION_FAILED(507, "엑셀 파일 생성 중 오류가 발생했습니다."),

    // purpose
    PURPOSE_NOT_FOUND(404, "존재하지 않는 방문목적입니다."),

    //user
    USER_NOT_FOUND(404, "해당 유저가 존재 하지 않습니다."),
    USER_MISMATCH(401, "유저가 일치 하지 않습니다."),
    PASSWORD_MISMATCH(401, "비밀 번호가 일치 하지 않습니다."),
    INVALID_USER(401, "유효 하지 않은 사용자입니다."),
    USER_EXIST(401, "유저가 이미 존재합니다."),
    COMPANION_NOT_FOUND(404, "존재하지 않는 동행인 ID입니다."),

    //admin
    ADMIN_NOT_FOUND(404, "해당 관리자 계정이 존재하지 않습니다."),
    INVALID_PASSWORD_CONFIRM(401, "비밀번호 확인이 일치하지 않습니다."),
    SAME_OLD_PASSWORD(400, "기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다."),

    //s3
    IMAGE_NOT_FOUND(404, "이미지를 찾을 수 없음"),
    FAILED_UPLOAD(1001, "업로드 실패"),
    FAILED_DELETE(1002, "삭제 실패"),

    //problem
    PROBLEM_NOT_FOUND(404, "Problem Not Found"),

    //completion
    COMPLETION_NOT_FOUND(404, "Completion Not Found"),

    // general
    BAD_REQUEST(400, "프론트 탓"),
    INTERNAL_SERVER_ERROR(500, "서버 탓");


    private final int statusCode;
    private final String ErrorMessage;
}
