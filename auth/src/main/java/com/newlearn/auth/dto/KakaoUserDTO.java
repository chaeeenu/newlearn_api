package com.newlearn.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@ToString(of = {"id", "connectedAt"}) // 민감한 정보는 toString에서 제외
public class KakaoUserDTO {

    @NotNull
    private Long id;                    // 회원번호

    @JsonProperty("has_signed_up")
    private Boolean hasSignedUp;        // 연결하기 호출의 완료여부 (false:연결대기상태, true:연결상태)

    @JsonProperty("connected_at")
    private OffsetDateTime connectedAt;  // 서비스에 연결 완료된 시각, UTC

    @JsonProperty("synched_at")
    private OffsetDateTime synchedAt;    // 카카오싱크 간편가입으로 로그인한 시각, UTC

    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Properties {
        private String nickname;

        @JsonProperty("profile_image")
        private String profileImage;

        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

    @Getter
    @NoArgsConstructor
    @ToString(exclude = {"email"}) // 이메일은 민감한 정보이므로 제외
    public static class KakaoAccount {
        @JsonProperty("profile_nickname_needs_agreement")
        private Boolean profileNicknameNeedsAgreement;

        @JsonProperty("profile_image_needs_agreement")
        private Boolean profileImageNeedsAgreement;

        private Profile profile;

        @JsonProperty("has_email")
        private Boolean hasEmail;

        @JsonProperty("email_needs_agreement")
        private Boolean emailNeedsAgreement;

        @JsonProperty("is_email_valid")
        private Boolean isEmailValid;

        @JsonProperty("is_email_verified")
        private Boolean isEmailVerified;

        private String email;

        @Getter
        @NoArgsConstructor
        @ToString
        public static class Profile {
            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;

            @JsonProperty("is_default_image")
            private Boolean isDefaultImage;
        }
    }
}
