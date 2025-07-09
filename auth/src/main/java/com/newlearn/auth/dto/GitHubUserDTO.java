package com.newlearn.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(exclude = "email")
public class GitHubUserDTO {
    private String login; // GitHub 사용자 ID
    private String id; // GitHub 사용자 고유 ID
    @JsonProperty("node_id")
    private String nodeId; // GitHub 사용자 노드 ID
    @JsonProperty("avatar_url")
    private String avatarUrl; // GitHub 사용자 아바타 URL
    @JsonProperty("gravatar_id")
    private String gravatarId; // GitHub 사용자 Gravatar ID
    private String url; // GitHub 사용자 프로필 URL
    @JsonProperty("html_url")
    private String htmlUrl; // GitHub 사용자 HTML 프로필 URL
    @JsonProperty("followers_url")
    private String followersUrl; // GitHub 사용자 팔로워 URL
    @JsonProperty("following_url")
    private String followingUrl; // GitHub 사용자 팔로잉 URL
    @JsonProperty("gists_url")
    private String gistsUrl; // GitHub 사용자 Gist URL
    @JsonProperty("starred_url")
    private String starredUrl; // GitHub 사용자 스타드 URL
    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl; // GitHub 사용자 구독 URL
    @JsonProperty("organizations_url")
    private String organizationsUrl; // GitHub 사용자 조직 URL
    @JsonProperty("repos_url")
    private String reposUrl; // GitHub 사용자 저장소 URL
    @JsonProperty("events_url")
    private String eventsUrl; // GitHub 사용자 이벤트 URL
    @JsonProperty("received_events_url")
    private String receivedEventsUrl; // GitHub 사용자 수신 이벤트 URL
    private String type; // GitHub 사용자 유형 (예: "User", "Organization")
    @JsonProperty("site_admin")
    private Boolean siteAdmin; // GitHub 사용자 사이트 관리자 여부
    private String name; // GitHub 사용자 이름
    private String company; // GitHub 사용자 소속 회사
    private String blog; // GitHub 사용자 블로그 URL
    private String location; // GitHub 사용자 위치
    private String email; // GitHub 사용자 이메일 (공개 여부에 따라 null일 수 있음)
    @JsonProperty("hireable")
    private Boolean hireable; // GitHub 사용자 채용 가능 여부
    private String bio; // GitHub 사용자 소개
    @JsonProperty("twitter_username")
    private String twitterUsername; // GitHub 사용자 트위터 사용자 이름
    @JsonProperty("public_repos")
    private Integer publicRepos; // GitHub 사용자 공개 저장소 수
    @JsonProperty("public_gists")
    private Integer publicGists; // GitHub 사용자 공개 Gist 수
    @JsonProperty("followers")
    private Integer followers; // GitHub 사용자 팔로워 수
    @JsonProperty("following")
    private Integer following; // GitHub 사용자 팔로잉 수
    @JsonProperty("created_at")
    private String createdAt; // GitHub 사용자 계정 생성일
    @JsonProperty("updated_at")
    private String updatedAt; // GitHub 사용자 계정 업데이트일
    @JsonProperty("private_gists")
    private Integer privateGists; // GitHub 사용자 비공개 Gist 수
    @JsonProperty("total_private_repos")
    private Integer totalPrivateRepos; // GitHub 사용자 비공개 저장소 총 수
    @JsonProperty("owned_private_repos")
    private Integer ownedPrivateRepos; // GitHub 사용자 소유 비공개 저장소 수
    @JsonProperty("disk_usage")
    private Integer diskUsage; // GitHub 사용자 디스크 사용량
    @JsonProperty("collaborators")
    private Integer collaborators; // GitHub 사용자 협업자 수
    @JsonProperty("two_factor_authentication")
    private Boolean twoFactorAuthentication; // GitHub 사용자 2단계 인증 여부
    @JsonProperty("plan")
    private Plan plan; // GitHub 사용자 플랜 정보

    @Getter
    @NoArgsConstructor
    @ToString
    public static class Plan {
        private String name; // 플랜 이름 (예: "free", "pro")
        private Integer space; // 플랜 저장 공간 (MB 단위)
        @JsonProperty("collaborators")
        private Integer collaborators; // 플랜 협업자 수
        @JsonProperty("private_repos")
        private Integer privateRepos; // 플랜 비공개 저장소 수
    }
}
