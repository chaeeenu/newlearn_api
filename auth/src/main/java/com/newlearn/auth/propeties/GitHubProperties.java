package com.newlearn.auth.propeties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.oauth2.github")
@Getter
@Setter
public class GitHubProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
