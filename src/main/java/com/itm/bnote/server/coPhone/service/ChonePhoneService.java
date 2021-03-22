package com.itm.bnote.server.coPhone.service;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.coPhone.model.ChonePhoneResponseModel;

public interface ChonePhoneService {

    public ChonePhoneResponseModel checkAppVersion(CommonRequestModel commonRequestModel) throws Exception;

    public ChonePhoneResponseModel checkExcutivesVersion(CommonRequestModel commonRequestModel) throws Exception;
}
