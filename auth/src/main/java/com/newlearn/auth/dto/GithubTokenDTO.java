package com.newlearn.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "accessToken") // 보안상 액세스 토큰은 toString에서 제외
public class GithubTokenDTO {

    @JsonProperty("access_token")
    @NotNull
    private String accessToken; // 액세스 토큰
    private String scope;
    @JsonProperty("token_type")
    private String tokenType; // 토큰 타입, 일반적으로 "bearer"로 설정됨
}
