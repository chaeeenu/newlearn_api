package com.newlearn.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoTokenReqeustDTO {
    
    private final String code;              // 토큰 받기 요청에 필요한 인가 코드
    private final String error;             // 인증 실패 시 반환되는 에러 코드
    private final String errorDescription;  // 인증 실패 시 반환되는 에러 메세지
    private final String state;             // 요청 시 전달한 state값과 동일한 값
}
