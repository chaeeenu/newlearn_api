package com.newlearn.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(title = "최상위 부모 DTO")
public class BaseDTO implements Serializable {

    /** 공통컬럼 필드 */
    @Schema(title = "내부식별자", description = "PK 역할")
    private Integer id;
    
    @Schema(title = "등록자 내부ID")
    private Integer regId;

    @Schema(title = "등록자 접속 IP")
    private String regIp;

    @Schema(title = "등록일시")
    private LocalDateTime regDtm;

    @Schema(title = "수정자 내부ID")
    private Integer modId;

    @Schema(title = "수정자 접속 IP")
    private String modIp;

    @Schema(title = "수정일시")
    private LocalDateTime modDtm;

    /** Response 필드 */

}
