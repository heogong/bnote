package com.itm.bnote.server.selfProgress.service;

import com.itm.bnote.server.api.model.AppVersionModel;
import com.itm.bnote.server.api.model.ExcutivesVersionModel;
import com.itm.bnote.server.common.model.MakeDataResponseModel;
import org.springframework.util.MultiValueMap;

public interface SelfProgressService {
    public MakeDataResponseModel updateExcutivesVersion(MultiValueMap<String, String> map) throws Exception;

    public MakeDataResponseModel updateAppVersion(AppVersionModel appVersionModel) throws Exception;

    public AppVersionModel updateAppVersion(String osType, String forceUp, String noticeMsg) throws Exception;

    public ExcutivesVersionModel updateOrgVersion(String forceUp, String comp) throws Exception;

    public String setNotice(String updateNoticeCode, String osType, String msg, String useYn) throws Exception;

    public MakeDataResponseModel changeTelNo() throws Exception;

}
