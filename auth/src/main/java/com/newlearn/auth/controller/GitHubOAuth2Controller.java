package com.newlearn.auth.controller;

import com.newlearn.auth.dto.GitHubUserDTO;
import com.newlearn.auth.dto.GithubTokenDTO;
import com.newlearn.auth.service.GitHubAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/oauth2/github")
@RequiredArgsConstructor
@Slf4j
public class GitHubOAuth2Controller {

    private final GitHubAuthService gitHubAuthService;

    @Value("${app.oauth2.github.client-id}")
    private String githubClientId;
    @Value("${app.oauth2.github.redirect-uri}")
    private String githubRedirectUri;
    private static final String githubAuthorizationUri = "https://github.com/login/oauth/authorize";

    @GetMapping("/login-url")
    public ResponseEntity<Map<String, String>> getGitHubLoginUrl() {
        String githubLoginUrl = githubAuthorizationUri
                + "?client_id=" + githubClientId
                + "&redirect_uri=" + githubRedirectUri;

        return ResponseEntity.ok(Map.of("loginUrl", githubLoginUrl));
    }

    @GetMapping("/github-login")
    public ResponseEntity<?> gitHubCallBack(@RequestParam String code, HttpServletResponse response) {

        try {
            log.info("GitHub OAuth2 Callback - Code: {}", code);

            // 1. GitHub에서 액세스 토큰을 요청
            GithubTokenDTO tokenResponse = gitHubAuthService.getAccessToken(code);
            String encodedAccessToken = URLEncoder.encode(tokenResponse.getAccessToken(), StandardCharsets.UTF_8);

            // 2. 액세스 토큰을 사용하여 사용자 정보를 요청
            GitHubUserDTO userInfo = gitHubAuthService.getUserInfo(tokenResponse.getAccessToken());
            String encodedLogin = URLEncoder.encode(userInfo.getLogin(), StandardCharsets.UTF_8);

            // 3. 리다이렉트 URI 생성 (accessToken을 query param 또는 fragment로 전달)
            String frontendRedirectUri = UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/callback/github")
                    .queryParam("accessToken", encodedAccessToken)
                    .queryParam("login", encodedLogin)
                    .build(false)
                    .toUriString();

            response.sendRedirect(frontendRedirectUri);
            return null;
        } catch (Exception e) {
            log.error("GitHub OAuth2 Callback Error: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", "GitHub OAuth2 callback failed"));
        }
    }
}
