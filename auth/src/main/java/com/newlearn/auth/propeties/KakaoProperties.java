package com.newlearn.auth.propeties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.oauth2.kakao")
@Getter
@Setter
public class KakaoProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
