package com.itm.bnote.server.organization.service;

import com.itm.bnote.server.common.model.MakeDataResponseModel;

import java.util.List;

public interface ExcutivesService<E> {

//    public MakeDataResponseModel makeExcutivesJson(String osType, String forceUp) throws Exception;
    public MakeDataResponseModel makeExcutivesJson(List<E> empInfoResult, String company, String forceUp) throws Exception;

}
