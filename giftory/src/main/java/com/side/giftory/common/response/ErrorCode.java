package com.side.giftory.common.response;

public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND", "사용자를 찾을 수 없습니다"),
    INVALID_TOKEN("INVALID_TOKEN", "토큰이 유효하지 않습니다"),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", "이미 존재하는 이메일입니다"),
    ACCESS_DENIED("ACCESS_DENIED" , "권한이 없습니다"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","서버 에러"),
    BAD_REQUEST("BAD_REQUEST", "잘못된 요청입니다.");
    

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
