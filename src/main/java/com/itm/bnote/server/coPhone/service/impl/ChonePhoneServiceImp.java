package com.itm.bnote.server.coPhone.service.impl;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.coPhone.model.CPAppVersionModel;
import com.itm.bnote.server.coPhone.model.CPExcutivesVersionModel;
import com.itm.bnote.server.coPhone.model.ChonePhoneResponseModel;
import com.itm.bnote.server.coPhone.repository.CPAppVersionRepository;
import com.itm.bnote.server.coPhone.repository.CPExcutivesVersionRepository;
import com.itm.bnote.server.coPhone.service.ChonePhoneService;
import com.itm.bnote.server.common.CommonUtil;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.exception.ExcuteiveException;
import com.itm.bnote.server.common.exception.RsaKeyException;
import com.itm.bnote.server.configuration.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChonePhoneServiceImp implements ChonePhoneService {

    @Autowired
    private CPAppVersionRepository cpAppVersionRepository;

    @Autowired
    private CPExcutivesVersionRepository cpExcutivesVersionRepository;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    RSAUtil rsaUtil;

    // 어플리케이션 다운로드 경로
    @Value("${file.url.buildfileDownLoadPath}")
    String fileUrlBuildFileDownLoadPath;

    // 조직도 파일 다운로드 경로
    @Value("${file.url.orgListDownLoadPath}")
    String fileUrlOrgListDownLoadPath;

    @Override
    public ChonePhoneResponseModel checkAppVersion(CommonRequestModel commonRequestModel) throws Exception {

        double userAppVersion = commonRequestModel.getAppVersion();
        ChonePhoneResponseModel chonePhoneResponseModel = new ChonePhoneResponseModel();

        List<CPAppVersionModel> cpAppVersionModelList = cpAppVersionRepository.findByDeviceOsTypeAndAppVersionGreaterThanOrderByIdxDesc(commonRequestModel.getDeviceOsType(), userAppVersion);

        if (cpAppVersionModelList.size() > 0) {

            CPAppVersionModel appVersionModel = cpAppVersionModelList.get(0);

            chonePhoneResponseModel.setAppUpdateYn("Y");
            chonePhoneResponseModel.setAppUpdateMessage(appVersionModel.getUpdateMessage() + " " + appVersionModel.getAppVersion());
            chonePhoneResponseModel.setAppUpdateUrl(fileUrlBuildFileDownLoadPath + appVersionModel.getAppFileName());
        }

        return chonePhoneResponseModel;
    }

    @Override
    public ChonePhoneResponseModel checkExcutivesVersion(CommonRequestModel commonRequestModel) throws Exception {

        ChonePhoneResponseModel chonePhoneResponseModel = new ChonePhoneResponseModel();

        CPExcutivesVersionModel cpExcutivesVersionModel = cpExcutivesVersionRepository.findByExcutivesMaxVersionModel();

        if (cpExcutivesVersionModel != null) {

            if (cpExcutivesVersionModel.getExcutivesMinorVersion() == commonRequestModel.getExcutivesVersion()) {
                chonePhoneResponseModel.setExcutivesUpdateYn("N");
            } else {
                // AES256암호화 키값을 RSA 공개키로 암호화
                if (commonRequestModel.getRsaKeyValue().toString().equals("") || commonRequestModel.getRsaKeyValue() == null) {
                    throw new RsaKeyException(CommonValue.ERROR_RSA_MESSAGE);
                } else {
                    String publicKeyResult = rsaUtil.encrypt(CommonValue.AES256_KEY_VALUE, commonRequestModel.getRsaKeyValue());
                    chonePhoneResponseModel.setExcutivesKey(publicKeyResult);
                }
                chonePhoneResponseModel.setExcutivesMajorVersion(cpExcutivesVersionModel.getExcutivesMajorVersion());
                chonePhoneResponseModel.setExcutivesMinorVersion(cpExcutivesVersionModel.getExcutivesMinorVersion());
                chonePhoneResponseModel.setExcutivesUpdateUrl(fileUrlOrgListDownLoadPath + cpExcutivesVersionModel.getJsonFilename());
                chonePhoneResponseModel.setExcutivesUpdateYn("Y");
            }
        } else {
            // 해당 앱 버전에 맞는 임직원 정보 테이블 매핑 정보 없음
            throw new ExcuteiveException(CommonValue.ERROR_EXCUTEIVE_NULL_MESSAGE);
        }
        return chonePhoneResponseModel;
    }
}
