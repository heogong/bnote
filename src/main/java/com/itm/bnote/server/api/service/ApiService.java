package com.itm.bnote.server.api.service;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.api.model.CommonResponseModel;

/**
 * @author Yu Byeong Ha
 * @Date 2018.04.26
 * @Comment : GSITMCall 단말 API
 */
public interface ApiService {

    public CommonResponseModel checkDeviceUuid(CommonRequestModel commonRequestModel) throws Exception;

    public CommonResponseModel checkAppVersion(CommonRequestModel commonRequestModel, CommonResponseModel commonResponseModel) throws Exception;

    public CommonResponseModel checkExcutivesVersion(CommonRequestModel commonRequestModel, CommonResponseModel commonResponseModel) throws Exception;

}
