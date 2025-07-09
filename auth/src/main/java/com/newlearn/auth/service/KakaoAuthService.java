package com.newlearn.auth.service;

import com.newlearn.auth.dto.KakaoTokenResponseDTO;
import com.newlearn.auth.dto.KakaoUserDTO;
import com.newlearn.auth.enums.KakaoAuthErrorType;
import com.newlearn.auth.exception.KakaoAuthException;
import com.newlearn.auth.propeties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthService {

    private final KakaoProperties kakaoProperties;
    private final WebClient webClient;

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    /**
     * Kakao OAuth2 인증을 통해 액세스 토큰 발급
     *
     * @param authorizationCode 카카오 인증 서버로부터 받은 인증 코드
     * @return KakaoTokenResponseDTO 액세스 토큰 응답 DTO
     */
    public KakaoTokenResponseDTO getAccessToken(String authorizationCode) {
        if(authorizationCode == null || authorizationCode.trim().isEmpty()) {
            throw new KakaoAuthException(KakaoAuthErrorType.INVALID_AUTHORIZATION_CODE, "인증 코드가 필요합니다.");
        }

        MultiValueMap<String, String> params = createTokenRequestParams(authorizationCode);

        try {
            KakaoTokenResponseDTO response = webClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body(BodyInserters.fromFormData(params))
                    .retrieve()
                    .bodyToMono(KakaoTokenResponseDTO.class)
                    .timeout(Duration.ofSeconds(10)) // 타임아웃 설정
                    .block();

            if(response == null) {
                throw new KakaoAuthException(KakaoAuthErrorType.API_RESPONSE_ERROR, "카카오 서버로부터 응답을 받지 못했습니다.");
            }
            log.info("카카오 토큰 발급 성공: {}", response.getAccessToken());
            return response;

        } catch (WebClientResponseException e) {
            log.error("카카오 토큰 발급 실패: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new KakaoAuthException(KakaoAuthErrorType.ACCESS_TOKEN_FETCH_ERROR, "카카오 액세스 토큰 발급에 실패했습니다.");
        } catch (Exception e) {
            log.error("카카오 토큰 발급 중 예상치 못한 오류 발생", e);
            throw new KakaoAuthException("카카오 토큰 발급 중 오류 발생: " + e);
        }
    }

    /**
     * 카카오 사용자 정보 조회
     *
     * @param accessToken 액세스 토큰
     * @return KakaoUserDTO 사용자 정보 DTO
     */
    public KakaoUserDTO getUserInfo(String accessToken) {
        if(accessToken == null || accessToken.isEmpty()) {
            throw new KakaoAuthException(KakaoAuthErrorType.INVALID_ACCESS_TOKEN, "카카오 액세스 토큰이 필요합니다.");
        }

        try {
            KakaoUserDTO userInfo = webClient.get()
                    .uri(KAKAO_USER_INFO_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(KakaoUserDTO.class)
                    .block();

            if(userInfo == null) {
                throw new KakaoAuthException(KakaoAuthErrorType.USER_INFO_FETCH_ERROR, "카카오 사용자 정보를 조회할 수 없습니다.");
            }
            log.info("카카오 사용자 정보 조회 성공: userId={}", userInfo.getId());
            return userInfo;

        } catch (WebClientResponseException e) {
            log.error("카카오 사용자 정보 조회 실패: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new KakaoAuthException(KakaoAuthErrorType.INVALID_ACCESS_TOKEN, "유효하지 않은 액세스 토큰입니다.");
            }
            throw new KakaoAuthException(KakaoAuthErrorType.USER_INFO_FETCH_ERROR, "카카오 사용자 정보 조회에 실패했습니다.");
        } catch (Exception e) {
            log.error("카카오 사용자 정보 조회 중 예상치 못한 오류 발생", e);
            throw new KakaoAuthException("카카오 사용자 정보 조회 중 예상치 못한 오류 발생: " + e);
        }
    }

    /**
     * 카카오 토큰 갱신
     *
     * @param refreshToken 리프레시 토큰
     * @return KakaoTokenResponseDTO 갱신된 액세스 토큰 응답 DTO
     */
    public KakaoTokenResponseDTO refreshToken(String refreshToken) {
        if(refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new KakaoAuthException(KakaoAuthErrorType.INVALID_REFRESH_TOKEN, "리프레시 토큰이 필요합니다.");
        }

        MultiValueMap<String, String> params = createRefreshTokenRequestParams(refreshToken);

        try {
            KakaoTokenResponseDTO response = webClient.post()
                    .uri(KAKAO_TOKEN_URL)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body(BodyInserters.fromFormData(params))
                    .retrieve()
                    .bodyToMono(KakaoTokenResponseDTO.class)
                    .block();

            if(response == null) {
                throw new KakaoAuthException(KakaoAuthErrorType.TOKEN_REFRESH_ERROR, "카카오 서버로부터 갱신된 토큰 응답을 받지 못했습니다.");
            }

            log.info("카카오 토큰 갱신 성공");
            return response;

        } catch (WebClientResponseException e) {
            log.error("카카오 토큰 갱신 실패: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new KakaoAuthException(KakaoAuthErrorType.INVALID_REFRESH_TOKEN, "유효하지 않은 리프레시 토큰입니다.");
            }

            throw new KakaoAuthException(KakaoAuthErrorType.TOKEN_REFRESH_ERROR, "카카오 토큰 갱신 실패");
        } catch (Exception e) {
            log.error("카카오 토큰 갱신 중 예상치 못한 오류 발생", e);
            throw new KakaoAuthException("카카오 토큰 갱신 중 오류 발생", e);
        }
    }

    /**
     * 토큰 발급 요청 파라미터 생성
     */
    private MultiValueMap<String, String> createTokenRequestParams(String authorizationCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoProperties.getClientId());
        params.add("redirect_uri", kakaoProperties.getRedirectUri());
        params.add("code", authorizationCode);

        // client_secret이 설정되어 있는 경우에만 추가
        if (kakaoProperties.getClientSecret() != null && !kakaoProperties.getClientSecret().trim().isEmpty()) {
            params.add("client_secret", kakaoProperties.getClientSecret());
        }

        return params;
    }

    /**
     * 리프레시 토큰 요청 파라미터 생성
     */
    private MultiValueMap<String, String> createRefreshTokenRequestParams(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", kakaoProperties.getClientId());
        params.add("refresh_token", refreshToken);

        // client_secret이 설정되어 있는 경우에만 추가
        if (kakaoProperties.getClientSecret() != null && !kakaoProperties.getClientSecret().trim().isEmpty()) {
            params.add("client_secret", kakaoProperties.getClientSecret());
        }

        return params;
    }
}
