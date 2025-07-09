package com.newlearn.service.dto.user;

import com.newlearn.service.annotation.CustomEncryption;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String pwd;
}
