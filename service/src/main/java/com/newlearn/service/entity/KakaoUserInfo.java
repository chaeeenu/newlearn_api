package com.newlearn.service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoUserInfo {

    private String id;
    private String nickname;
    private String profileImageUrl;
    private String thumbnailImageUrl;

}
