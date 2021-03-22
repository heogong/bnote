package com.itm.bnote.server.api.service.impl;


import com.itm.bnote.server.api.model.*;
import com.itm.bnote.server.api.repository.AppLogRepository;
import com.itm.bnote.server.api.repository.AppUuidRepository;
import com.itm.bnote.server.api.repository.AppVersionRepository;
import com.itm.bnote.server.api.repository.ExcutivesVersionRepository;
import com.itm.bnote.server.api.service.ApiService;
import com.itm.bnote.server.common.CommonUtil;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.exception.FailUuidException;
import com.itm.bnote.server.common.exception.RsaKeyException;
import com.itm.bnote.server.configuration.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * @author Yu Byeong Ha
 * @Date 2018.04.26
 * @Comment : GSITMCall 단말 API
 */
@Service
public class ApiServiceImp implements ApiService {

    @Autowired
    CommonUtil commonUtil;
    //
    @Autowired
    RSAUtil rsaUtil;

    @Autowired
    private AppVersionRepository appVersionRepository;

    @Autowired
    private AppUuidRepository appUuidRepository;

    @Autowired
    private AppLogRepository appLogRepository;

    @Autowired
    private ExcutivesVersionRepository excutivesVersionRepository;

    @PersistenceContext
    EntityManager em;

    // 어플리케이션 다운로드 경로
    @Value("${file.url.buildfileDownLoadPath}")
    String fileUrlBuildFileDownLoadPath;


    // 조직도 파일 다운로드 경로
    @Value("${file.url.orgListDownLoadPath}")
    String fileUrlOrgListDownLoadPath;


    @Override
    public CommonResponseModel checkDeviceUuid(CommonRequestModel commonRequestModel) throws Exception {
        CommonResponseModel commonResponseModel = new CommonResponseModel();

        String deviceUuid = commonRequestModel.getDeviceUuid();
        String deviceUuidResult = "";

        // UUID 체크 및 생성
        if (!deviceUuid.equals("")) {
            try {
                AppUuidModel appUuidModel = appUuidRepository.findByDeviceUuid(deviceUuid);
                deviceUuidResult = appUuidModel.getDeviceUuid();

            } catch (NullPointerException ex) {
                throw new FailUuidException(CommonValue.ERROR_UUID_MESSAGE);
            }
        } else {
            deviceUuidResult = commonUtil.createUuid();

            try {
                AppUuidModel newUuid = new AppUuidModel();

                newUuid.setDeviceUuid(deviceUuidResult);
                newUuid.setRegDate(new Date());

                appUuidRepository.save(newUuid);

                commonRequestModel.setDeviceUuid(deviceUuidResult);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        // 접속 로그 기록
        try {
            AppLogModel newLog = new AppLogModel();

            newLog.setAppPackageName(commonRequestModel.getAppPackageName());
            newLog.setOrgVersion(commonRequestModel.getExcutivesVersion());
            newLog.setAppVersion(commonRequestModel.getAppVersion());
            newLog.setDeviceOsType(commonRequestModel.getDeviceOsType());
            newLog.setDeviceOsVersion(commonRequestModel.getDeviceOsVersion());
            newLog.setDeviceUuid(commonRequestModel.getDeviceUuid());
            newLog.setDeviceModel(commonRequestModel.getDeviceModel());
            newLog.setRegDate(new Date());

            appLogRepository.save(newLog);

            commonResponseModel.setReturnCode(CommonValue.SUCCESS_CODE);
            commonResponseModel.setReturnMessage(CommonValue.SUCCESS_MESSAGE);
            commonResponseModel.setDeviceUuid(deviceUuidResult);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return commonResponseModel;
    }

    @Override
    public CommonResponseModel checkAppVersion(CommonRequestModel commonRequestModel, CommonResponseModel commonResponseModel) throws Exception {
        double userAppVersion = commonRequestModel.getAppVersion();

        List<AppVersionModel> appVersionModelList = appVersionRepository.findByDeviceOsTypeAndAppVersionGreaterThanOrderByAppSeqDesc(commonRequestModel.getDeviceOsType(), userAppVersion);

        commonResponseModel.setAppVersion(commonRequestModel.getAppVersion());  // 요청된 app 버전 정보 set - 업데이트 없을 경우 표출

        // APP 업데이트 존재
        if (appVersionModelList.size() > 0) {
            commonResponseModel.setReturnCode(CommonValue.APP_NORMAL_UPDATE_CODE);
            commonResponseModel.setReturnMessage(CommonValue.APP_NORMAL_UPDATE_MESSAGE);

            // 강제업데이트 여부 확인
            for (AppVersionModel appVersion : appVersionModelList) {
                if (appVersion.getForceUpdate().equals("Y")) {
                    commonResponseModel.setAppForceUpdate(appVersion.getForceUpdate());
                    commonResponseModel.setReturnCode(CommonValue.APP_FORCE_UPDATE_CODE);
                    commonResponseModel.setReturnMessage(CommonValue.APP_FORCE_UPDATE_MESSAGE);
                    break;
                }
            }

            AppVersionModel appVersionModel = appVersionModelList.get(0);

            commonResponseModel.setAppUpdateYn("Y");
//            commonResponseModel.setAppUpdateMessage(appVersionModel.getUpdateMessage() + "-" + appVersionModel.getAppVersion());
            commonResponseModel.setAppVersion(appVersionModel.getAppVersion());
            commonResponseModel.setAppUpdateUrl(fileUrlBuildFileDownLoadPath + commonRequestModel.getDeviceOsType() +"/"+ appVersionModel.getAppFileName());
        }

        return commonResponseModel;
    }

    @Override
    public CommonResponseModel checkExcutivesVersion(CommonRequestModel commonRequestModel, CommonResponseModel commonResponseModel) throws Exception {

        // ios / android 구분 필요 여부 확인
//        ExcutivesVersionModel excutivesVersionModel = excutivesVersionRepository.findByExcutivesVersion(
//                commonRequestModel.getAppVersion(),
//                commonRequestModel.getDeviceOsType()
//        );

        List<ExcutivesVersionModel> excutivesVersionList = excutivesVersionRepository.findByCompAndExcutivesMinorVersionGreaterThanOrderBySeqDesc(commonRequestModel.getCompany(), commonRequestModel.getExcutivesVersion());

        commonResponseModel.setExcutivesVersion(commonRequestModel.getExcutivesVersion()); // 요청된 조직도 버전 정보 set - 업데이트 없을 경우 버전정보 표출

        // 조직도 업데이트 존재
        if (excutivesVersionList.size() > 0) {
            commonResponseModel.setReturnCode(CommonValue.ORG_NORMAL_UPDATE_CODE);
            commonResponseModel.setReturnMessage(CommonValue.ORG_NORMAL_UPDATE_MESSAGE);

            ExcutivesVersionModel excutivesVersion = excutivesVersionList.get(0);

            if (commonRequestModel.getRsaKeyValue().toString().equals("") || commonRequestModel.getRsaKeyValue() == null) {
                throw new RsaKeyException(CommonValue.ERROR_RSA_MESSAGE);
            } else {
                String publicKeyResult = rsaUtil.encrypt(CommonValue.AES256_KEY_VALUE, commonRequestModel.getRsaKeyValue());
                commonResponseModel.setExcutivesKey(publicKeyResult);
            }

            // 강제업데이트 여부 확인
            for (ExcutivesVersionModel orgVersion : excutivesVersionList) {
                if (orgVersion.getForceUpdate().equals("Y")) {
                    commonResponseModel.setOrgForceUpdateYn(orgVersion.getForceUpdate());
                    commonResponseModel.setReturnCode(CommonValue.ORG_FORCE_UPDATE_CODE);
                    commonResponseModel.setReturnMessage(CommonValue.ORG_FORCE_UPDATE_MESSAGE);
                    break;
                }
            }

            commonResponseModel.setExcutivesUpdateYn("Y");
            commonResponseModel.setExcutivesVersion(excutivesVersion.getExcutivesMinorVersion());
            commonResponseModel.setExcutivesUpdateUrl(fileUrlOrgListDownLoadPath + excutivesVersion.getJsonFilename());
        }

        return commonResponseModel;
    }
}
