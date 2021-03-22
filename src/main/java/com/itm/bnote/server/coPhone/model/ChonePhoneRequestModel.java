package com.itm.bnote.server.coPhone.model;

import com.itm.bnote.server.common.CommonValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

public @Data
class ChonePhoneRequestModel {
    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String appPackageName;            // APP 패키지명

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private double appVersion;                // App 버전

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String deviceOsType;            // 사용자 디바이스 OS 타입 : ios or and

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String deviceOsVersion;            // 사용자 디바이스 OS의 버전

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String rsaKeyValue;                // 디바이스에서 전송하는 rsa 공개키값

    private double excutivesVersion;    // 사용자 임직원 정보 Minor 버전
    private String deviceUuid = "";                // 사용자 디바이스 UUID : 값이 없을 경우 서버에서 생성해 줌
    private double excutivesMajorVersion;    // 사용자 임직원 정보 Major 버전
    private String deviceModel;                // 사용자 디바이스 모델명
    private String regDate;
}
