package com.newlearn.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status implements CommonCodeType {

    USER_ACTIVE("USER_STATUS", "01", "유저 활성화", "사용자계정 활성화상태"),
    USER_DEACITVE("USER_STATUS", "02", "유저 비활성화", "사용자계정 비활성화상태"),;

    private final String codeId;
    private final String code;
    private final String codeName;
    private final String desc;

    @Override
    public String getCodeId() {
        return this.codeId;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getCodeName() {
        return this.codeName;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }
}
