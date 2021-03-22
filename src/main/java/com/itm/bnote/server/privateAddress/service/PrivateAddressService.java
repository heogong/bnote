package com.itm.bnote.server.privateAddress.service;

import com.itm.bnote.server.api.model.CommonResponseModel;
import com.itm.bnote.server.common.model.CommonReturnModel;
import com.itm.bnote.server.privateAddress.model.PrivateAddressModel;

import java.util.Map;

public interface PrivateAddressService {

    public CommonReturnModel addPrivateAddress2(PrivateAddressModel privateAddressModel) throws Exception;

    public CommonReturnModel addPrivateAddress(PrivateAddressModel privateAddressModel) throws Exception;

    public Map<String, Object> getParseString() throws Exception;
}
