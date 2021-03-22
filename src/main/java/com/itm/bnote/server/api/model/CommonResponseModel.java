package com.itm.bnote.server.api.model;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.model.CommonReturnModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
public @Data class CommonResponseModel extends CommonReturnModel {
    private String deviceUuid;
    private String appUpdateYn = "N";
    private String appUpdateUrl;
    private String excutivesUpdateYn = "N";
    private String orgForceUpdateYn = "N";
    private double excutivesVersion;
    private String excutivesUpdateUrl;
    private String excutivesKey;
    private String noticeCode = CommonValue.SUCCESS_CODE;
    private String noticeMessage = "";
    //    private double excutivesMajorVersion;
    //    private Map excutivesUpdateUrl;

    // 모바일 앱스토어 api 리턴값 - <common>
    private String isNew = "N";
    private String isPwChange = "N";
    private String pwChangePrompt;
    private String isPwPolicy = "N";

    // 모바일 앱스토어 api 리턴값 - <responseInfo>
    private String fileSize;
    private String newId;
    private String appForceUpdate = "N";
    private double appVersion;

}
