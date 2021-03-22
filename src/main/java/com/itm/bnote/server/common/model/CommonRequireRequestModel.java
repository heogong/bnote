package com.itm.bnote.server.common.model;

import com.itm.bnote.server.common.CommonValue;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Date 2019.07.31
 * @Comment : 요청 파라미터 공통 필수값
 *
 */
public @Data
class CommonRequireRequestModel {

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String appPackageName;            // APP 패키지명

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private double appVersion;                // App 버전

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String deviceOsType;            // 사용자 디바이스 OS 타입 : ios or and

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String deviceOsVersion;            // 사용자 디바이스 OS의 버전
}
