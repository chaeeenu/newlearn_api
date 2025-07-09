package com.newlearn.service.dto.user;

import lombok.*;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private String accessToken;
    private String refreshToken;
}
