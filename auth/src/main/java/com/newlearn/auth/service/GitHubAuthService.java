package com.newlearn.auth.service;

import com.newlearn.auth.dto.GitHubUserDTO;
import com.newlearn.auth.dto.GithubTokenDTO;
import com.newlearn.auth.propeties.GitHubProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubAuthService {

    private final GitHubProperties gitHubProperties;
    private final WebClient webClient;

    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GITHUB_USER_INFO_URL = "https://api.github.com/user";

    /**
     * GitHub OAuth2 인증을 통해 액세스 토큰 발급
     *
     * @param authorizationCode GitHub OAuth2 인증 서버로부터 받은 인증 코드
     * @return GithubTokenDTO 액세스 토큰 응답 DTO
     */
    public GithubTokenDTO getAccessToken(String authorizationCode) {
        if(authorizationCode == null || authorizationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Authorization code is required");
        }

        MultiValueMap<String, String> params = createTokenRequestParams(authorizationCode);

        try {
            GithubTokenDTO response = webClient.post()
                    .uri(GITHUB_TOKEN_URL)
                    .header("Accept", "application/json")
                    .bodyValue(params)
                    .retrieve()
                    .bodyToMono(GithubTokenDTO.class)
                    .block();

            if (response == null || response.getAccessToken() == null) {
                throw new IllegalArgumentException("Failed to retrieve access token from GitHub");
            }

            log.info("GitHub access token retrieved successfully: {}", response.getAccessToken());
            return response;

        } catch (Exception e) {
            log.error("Error retrieving access token from GitHub: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve access token from GitHub", e);
        }
    }

    /**
     * @param accessToken GitHub에서 발급받은 액세스 토큰
     * @return
     */
    public GitHubUserDTO getUserInfo(String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Access token is required");
        }

        try {
            return webClient.get()
                    .uri(GITHUB_USER_INFO_URL)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(GitHubUserDTO.class)
                    .block();
        } catch (Exception e) {
            log.error("Error retrieving user info from GitHub: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve user info from GitHub", e);
        }
    }

    /**
     * GitHub 액세스 토큰 발급 요청 파라미터 생성
     */
    private MultiValueMap<String, String> createTokenRequestParams(String authorizationCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", gitHubProperties.getClientId());
        params.add("client_secret", gitHubProperties.getClientSecret());
        params.add("redirect_uri", gitHubProperties.getRedirectUri());
        params.add("code", authorizationCode);

        return params;
    }
}
