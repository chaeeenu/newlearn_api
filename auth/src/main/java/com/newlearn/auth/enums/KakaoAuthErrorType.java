package com.newlearn.auth.enums;

public enum KakaoAuthErrorType {

    INVALID_ACCESS_TOKEN("KAO001", "유효하지 않은 액세스 토큰"),
    EXPIRED_ACCESS_TOKEN("KAO002", "만료된 액세스 토큰"),
    INVALID_REFRESH_TOKEN("KAO003", "유효하지 않은 리프레시 토큰"),
    EXPIRED_REFRESH_TOKEN("KAO004", "만료된 리프레시 토큰"),
    INVALID_AUTHORIZATION_CODE("KAO005", "유효하지 않은 인증 코드"),
    NETWORK_ERROR("KAO006", "네트워크 오류"),
    API_RESPONSE_ERROR("KAO007", "카카오 API 응답 오류"),
    ACCESS_TOKEN_FETCH_ERROR("KAO008", "액세스 토큰 발급 실패"),
    USER_INFO_FETCH_ERROR("KAO009", "사용자 정보 조회 실패"),
    TOKEN_REFRESH_ERROR("KAO010", "토큰 갱신 실패"),
    INVALID_CLIENT_ID("KAO011", "유효하지 않은 클라이언트 ID"),
    INVALID_CLIENT_SECRET("KAO012", "유효하지 않은 클라이언트 시크릿"),
    USER_CANCELLED("KAO013", "사용자가 인증을 취소했습니다"),
    UNKNOWN_ERROR("KAO999", "알 수 없는 오류");

    private final String code;
    private final String message;

    KakaoAuthErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
