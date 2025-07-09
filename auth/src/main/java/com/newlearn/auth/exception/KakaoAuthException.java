package com.newlearn.auth.exception;

import com.newlearn.auth.enums.KakaoAuthErrorType;

public class KakaoAuthException extends RuntimeException {

    private final KakaoAuthErrorType errorType;
    private final String errorCode;
    private final int httpStatusCode;
    private final String detailMessage;
    
    // 기본 생성자
    public KakaoAuthException(String message) {
        super(message);
        this.errorType = KakaoAuthErrorType.UNKNOWN_ERROR;
        this.errorCode = errorType.getCode();
        this.httpStatusCode = 500;
        this.detailMessage = message;
    }

    public KakaoAuthException(String message, Throwable cause) {
        super(message, cause);
        this.errorType = KakaoAuthErrorType.UNKNOWN_ERROR;
        this.errorCode = errorType.getCode();
        this.httpStatusCode = 500;
        this.detailMessage = message;
    }

    // 에러 타입을 지정하는 생성자
    public KakaoAuthException(KakaoAuthErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.errorCode = errorType.getCode();
        this.httpStatusCode = determineHttpStatusCode(errorType);
        this.detailMessage = errorType.getMessage();
    }

    public KakaoAuthException(KakaoAuthErrorType errorType, String detailMessage) {
        super(detailMessage);
        this.errorType = errorType;
        this.errorCode = errorType.getCode();
        this.httpStatusCode = determineHttpStatusCode(errorType);
        this.detailMessage = detailMessage;
    }

    public KakaoAuthException(KakaoAuthErrorType errorType, Throwable cause) {
        super(errorType.getMessage(), cause);
        this.errorType = errorType;
        this.errorCode = errorType.getCode();
        this.httpStatusCode = determineHttpStatusCode(errorType);
        this.detailMessage = errorType.getMessage();
    }

    public KakaoAuthException(KakaoAuthErrorType errorType, String detailMessage, Throwable cause) {
        super(detailMessage, cause);
        this.errorType = errorType;
        this.errorCode = errorType.getCode();
        this.httpStatusCode = determineHttpStatusCode(errorType);
        this.detailMessage = detailMessage;
    }

    // HTTP 상태 코드 결정 메서드
    private int determineHttpStatusCode(KakaoAuthErrorType errorType) {
        switch (errorType) {
            case INVALID_ACCESS_TOKEN:
            case EXPIRED_ACCESS_TOKEN:
            case INVALID_REFRESH_TOKEN:
            case EXPIRED_REFRESH_TOKEN:
                return 401; // Unauthorized
            case INVALID_AUTHORIZATION_CODE:
            case INVALID_CLIENT_ID:
            case INVALID_CLIENT_SECRET:
                return 400; // Bad Request
            case USER_CANCELLED:
                return 403; // Forbidden
            case NETWORK_ERROR:
            case API_RESPONSE_ERROR:
                return 502; // Bad Gateway
            case USER_INFO_FETCH_ERROR:
            case TOKEN_REFRESH_ERROR:
                return 503; // Service Unavailable
            default:
                return 500; // Internal Server Error
        }
    }

    // Getter 메서드들
    public KakaoAuthErrorType getErrorType() {
        return errorType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    // 사용자 친화적인 메시지 반환
    public String getUserFriendlyMessage() {
        switch (errorType) {
            case INVALID_ACCESS_TOKEN:
            case EXPIRED_ACCESS_TOKEN:
                return "로그인이 만료되었습니다. 다시 로그인해주세요.";
            case NETWORK_ERROR:
                return "네트워크 연결을 확인해주세요.";
            case USER_CANCELLED:
                return "로그인이 취소되었습니다.";
            case API_RESPONSE_ERROR:
                return "카카오 서버에 일시적인 문제가 발생했습니다. 잠시 후 다시 시도해주세요.";
            default:
                return "로그인 중 오류가 발생했습니다. 다시 시도해주세요.";
        }
    }

    // 로그용 상세 정보 반환
    public String getLogMessage() {
        return String.format("[%s] %s - %s",
                errorCode,
                errorType.name(),
                detailMessage != null ? detailMessage : getMessage());
    }

    // toString 오버라이드
    @Override
    public String toString() {
        return String.format("KakaoAuthException{errorType=%s, errorCode='%s', httpStatusCode=%d, message='%s'}",
                errorType, errorCode, httpStatusCode, getMessage());
    }
}
