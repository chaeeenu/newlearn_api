package com.newlearn.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = {"accessToken", "refreshToken"}) // 보안상 토큰은 제외
public class KakaoTokenResponseDTO {

    @JsonProperty("token_type")
    @NotBlank
    private String tokenType;   // 토큰 타입, bearer 로 고정

    @JsonProperty("access_token")
    @NotBlank
    @Size(min = 1, max = 2048)
    private String accessToken; // 액세스 토큰

    @JsonProperty("id_token")
    private String idToken; // ID 토큰 (OpenID Connect 확장 기능으로 발급하는 ID 토큰, Base64 인코딩된 사용자 인증정보 포함)

    @JsonProperty("expires_in")
    @NotNull
    @Min(1)
    private Integer expiresIn; // 액세스 토큰과 ID토큰의 만료 시간 (초 단위)

    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken; // 사용자 리프레시 토큰 값

    @JsonProperty("refresh_token_expires_in")
    @NotBlank
    private Integer refreshTokenExpiresIn; // 리프레시 토큰 만료 시간 (초 단위)

    @JsonProperty("scope")
    private String scope; // 인증된 사용자의 정보 조회 권한 범위, 범위가 여러개일 경우, 공백으로 구분
}