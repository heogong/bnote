package com.itm.bnote.server.mobileAppstore.service;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.api.model.CommonResponseModel;

/**
 * @author Yu Byeong Ha
 * @Date 2018.04.26
 * @Comment : GSITMCall 단말 API
 */
public interface MobileAppstoreService {

    public CommonResponseModel APIcheckAppVersion(CommonRequestModel commonRequestModel, CommonResponseModel commonResponseModel) throws Exception;

}
