package com.newlearn.service.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.newlearn.service.annotation.CustomEncryption;
import com.newlearn.service.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Schema(title = "사용자 요청 DTO")
public class UserDTO extends BaseDTO {

    @NotBlank
    private String email;
    @CustomEncryption
    private String pwd;
    private String name;
    private String mobilePhoneNum;
    private String status;
    private String profileImageUrl;
}
