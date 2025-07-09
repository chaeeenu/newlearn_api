package com.newlearn.auth.controller;

import com.newlearn.auth.dto.KakaoTokenResponseDTO;
import com.newlearn.auth.dto.KakaoUserDTO;
import com.newlearn.auth.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2/kakao")
@RequiredArgsConstructor
@Slf4j
public class KakaoOAuth2Controller {

    private final KakaoAuthService kakaoAuthService;

    @Value("${app.oauth2.kakao.client-id}")
    private String kakaoClientId;
    @Value("${app.oauth2.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    /*@Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;*/
    private static final String kakaoAuthorizationUri = "https://kauth.kakao.com/oauth/authorize";

    /**
     * 카카오 로그인 URL을 생성하여 반환
     * @return ResponseEntity<Map<String, String>> 카카오 로그인 URL을 포함한 응답
     */
    @GetMapping("/login-url")
    public ResponseEntity<Map<String, String>> getKakaoLoginUrl() {
        String kakaoLoginUrl = kakaoAuthorizationUri
                + "?response_type=code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&prompt=login";

        return ResponseEntity.ok(Map.of("loginUrl", kakaoLoginUrl));
    }

    /**
     * 카카오 OAuth2 콜백 처리
     * @param code 인증 코드
     * @param state 상태 값
     * @return ResponseEntity<?> 카카오 로그인 처리 결과
     */
    @GetMapping("/kakao-login")
    public ResponseEntity<?> kakaoCallBack(@RequestParam String code, @RequestParam(required = false) String state, HttpServletResponse response) {

        try {
            log.info("Kakao OAuth2 Callback - Code: {}, State: {}", code, state);
            
            // 1. 토큰 발급
            KakaoTokenResponseDTO tokenResponse = kakaoAuthService.getAccessToken(code);
            String encodedAccessToken = URLEncoder.encode(tokenResponse.getAccessToken(), StandardCharsets.UTF_8);
            // 2. 사용자 정보 조회
            KakaoUserDTO userInfo = kakaoAuthService.getUserInfo(tokenResponse.getAccessToken());
            String nickname = userInfo.getProperties() != null ? userInfo.getProperties().getNickname() : "";
            String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

            // 3. 리다이렉트 URI 생성 (accessToken을 query param 또는 fragment로 전달)
            String frontendRedirectUri = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/callback/kakao")
                    .queryParam("token", encodedAccessToken)
                    .queryParam("nickname", encodedNickname)
                    .build(false)
                    .toUriString();

            response.sendRedirect(frontendRedirectUri); // 실제 리다이렉트 처리
            return null;
        } catch(Exception e) {
            log.error("카카오 로그인 처리 실패", e);
            // return createErrorResponse("카카오 로그인 처리 중 오류가 발생했습니다.", e.getMessage());
            try {
                response.sendRedirect("http://localhost:3000/login?error=kakao");
            } catch (IOException ioException) {
                log.error("리다이렉트 중 오류 발생", ioException);
            }

            return  null;
        }
    }

    /**
     * 토큰 갱신
     *
     * @param request 요청 본문에 포함된 refresh_token
     * @return ResponseEntity<?> 갱신된 토큰 정보
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refresh_token");

            if(refreshToken == null || refreshToken.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "refresh_token이 필요합니다."));
            }

            KakaoTokenResponseDTO tokenResponse = kakaoAuthService.refreshToken(refreshToken);

            return ResponseEntity.ok(createTokenResponse(tokenResponse));

        } catch (Exception e) {
            log.error("토큰 갱신 실패", e);
            return createErrorResponse("토큰 갱신 실패", e.getMessage());
        }
    }

    /**
     * 사용자 정보 응답 생성
     */
    private Map<String, Object> createUserResponse(KakaoUserDTO kakaoUserDTO) {
        Map<String ,Object> user = new HashMap<>();
        user.put("id", kakaoUserDTO.getId());
        // null 체크 강화
        if (kakaoUserDTO.getProperties() != null) {
            user.put("nickname", kakaoUserDTO.getProperties().getNickname());
            user.put("profileImage", kakaoUserDTO.getProperties().getProfileImage());
        }

        if(kakaoUserDTO.getKakaoAccount() != null) {
            user.put("email", kakaoUserDTO.getKakaoAccount().getEmail());
            if(kakaoUserDTO.getKakaoAccount().getProfile() != null) {
                user.put("profileImageUrl", kakaoUserDTO.getKakaoAccount().getProfile().getProfileImageUrl());
            }
        }

        return user;
    }

    /**
     * 토큰 응답 생성
     */
    private Map<String, Object> createTokenResponse(KakaoTokenResponseDTO tokenResponse) {
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("access_token", tokenResponse.getAccessToken());
        tokens.put("refresh_token", tokenResponse.getRefreshToken());
        tokens.put("token_type", tokenResponse.getTokenType());
        tokens.put("expires_in", tokenResponse.getExpiresIn());

        return tokens;
    }

    /**
     * 에러 응답 생성 (중복 코드 제거)
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(String error, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", error);
        errorResponse.put("message", message);

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
