package com.itm.bnote.server.common.model;

import com.itm.bnote.server.api.model.AppVersionModel;
import com.itm.bnote.server.api.model.ExcutivesVersionModel;
import com.itm.bnote.server.common.CommonValue;
import lombok.Getter;
import lombok.Setter;

public class MakeDataResponseModel {
    @Getter @Setter
    private String returnCode = CommonValue.SUCCESS_CODE;
    @Getter @Setter
    private String returnMessage = CommonValue.SUCCESS_MESSAGE;
    @Getter @Setter
    private double orgVersion;
    @Getter @Setter
    private String orgFileName;
    @Getter @Setter
    private String orgExtFileName;
    @Getter @Setter
    private String forceUpdate;
    @Getter @Setter
    private double appVersion;
    @Getter @Setter
    private String updateOsType;
    @Getter @Setter
    private String updateAppFileName;

    public void setOrgData(ExcutivesVersionModel model) {
        this.updateOsType = model.getDeviceOsType();
        this.orgFileName = model.getJsonFilename();
        this.orgExtFileName = model.getExtJsonFilename();
        this.orgVersion = model.getExcutivesMinorVersion();
        this.forceUpdate = model.getForceUpdate();
    }

    public void setAppData(AppVersionModel model) {
        this.appVersion = model.getAppVersion();
        this.updateOsType = model.getDeviceOsType();
        this.updateAppFileName = model.getAppFileName();
        this.orgExtFileName = model.getForceUpdate();
        this.forceUpdate = model.getForceUpdate();
    }
}
