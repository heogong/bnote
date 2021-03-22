package com.itm.bnote.server.api.model;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.model.CommonRequireRequestModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
public @Data
class CommonRequestModel extends CommonRequireRequestModel {

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String rsaKeyValue;                // 디바이스에서 전송하는 rsa 공개키값

    private String company = CommonValue.ALL_COMPANY;
    private double excutivesVersion;    // 사용자 임직원 정보 Minor 버전
    private String deviceUuid = "";                // 사용자 디바이스 UUID : 값이 없을 경우 서버에서 생성해 줌
    private double excutivesMajorVersion;    // 사용자 임직원 정보 Major 버전
    private String deviceModel;                // 사용자 디바이스 모델명
    private String regDate;

    private String appId;
    private String curVersion;
    private String empId;
    private String password;
    private String phoneNumber;
    private String imei;
    private String macAddress;
    private String notiDeviceId;
    private String menuInterfaceId;
    private String ip;
}
