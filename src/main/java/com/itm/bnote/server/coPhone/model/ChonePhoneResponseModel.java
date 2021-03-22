package com.itm.bnote.server.coPhone.model;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public @Data class ChonePhoneResponseModel {
    private String returnCode;
    private String returnMessage;
    private String deviceUuid;
    private String appUpdateYn = "N";
    private String appUpdateMessage;
    private String appUpdateUrl;
    private String excutivesUpdateYn;
    private double excutivesMajorVersion;
    private double excutivesMinorVersion;
    private String excutivesUpdateUrl;
    private String excutivesKey;
}
