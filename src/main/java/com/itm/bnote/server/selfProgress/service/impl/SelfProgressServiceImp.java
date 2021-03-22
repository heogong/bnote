package com.itm.bnote.server.selfProgress.service.impl;

import com.itm.bnote.server.api.model.AppVersionModel;
import com.itm.bnote.server.api.model.AppVersionNoticeModel;
import com.itm.bnote.server.api.model.ExcutivesVersionModel;
import com.itm.bnote.server.api.repository.AppVersionNoticeRepository;
import com.itm.bnote.server.api.repository.AppVersionRepository;
import com.itm.bnote.server.api.repository.ExcutivesVersionRepository;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.model.MakeDataResponseModel;
import com.itm.bnote.server.configuration.FileHashUtil;
import com.itm.bnote.server.organization.model.OrgEmpInfoModel;
import com.itm.bnote.server.organization.repository.OrgEmpInfoRepository;
import com.itm.bnote.server.selfProgress.service.SelfProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SelfProgressServiceImp implements SelfProgressService {

    @Autowired
    private AppVersionRepository appVersionRepository;

    @Autowired
    private AppVersionNoticeRepository appVersionNoticeRepository;

    @Autowired
    private ExcutivesVersionRepository excutivesVersionRepository;

    @Autowired
    private OrgEmpInfoRepository orgEmpInfoRepository;

    @Autowired
    private FileHashUtil fileHashUtil;

    @Override
    public MakeDataResponseModel updateExcutivesVersion(MultiValueMap<String, String> map) throws Exception {
        MakeDataResponseModel makeDataResponseModel = new MakeDataResponseModel();

        boolean extOrgCompany = map.get("extOrgCompany").get(0).equals("true");
        String dbFileName = map.get("dbFileName").get(0);
        String osType = map.get("osType").get(0);
        String forceUp = map.get("forceUp").get(0);
        String company = map.get("company").get(0);

        try {
            List<ExcutivesVersionModel> excutivesVersionList = excutivesVersionRepository.findByOrderBySeqDesc();
            ExcutivesVersionModel excutivesVersion = excutivesVersionList.size() > 0 ? excutivesVersionList.get(0) : new ExcutivesVersionModel();
            ExcutivesVersionModel newExcutivesVersionModel = new ExcutivesVersionModel();

            newExcutivesVersionModel.setExcutivesVersionSize(excutivesVersionList.size()); // 조직도 size set
            newExcutivesVersionModel.setExtOrgCompany(extOrgCompany); // 내부 or 외부 조직도 업데이트 체크

            newExcutivesVersionModel.setExcutivesMajorVersion(excutivesVersion.getExcutivesMajorVersion());
            newExcutivesVersionModel.setExcutivesMinorVersion(excutivesVersion.getExcutivesMinorVersion());
            newExcutivesVersionModel.setJsonFilename(dbFileName, excutivesVersion.getJsonFilename());
            newExcutivesVersionModel.setExtJsonFilename(dbFileName, excutivesVersion.getExtJsonFilename());
            newExcutivesVersionModel.setDeviceOsType(osType);
            newExcutivesVersionModel.setComp(company);
            newExcutivesVersionModel.setForceUpdate(forceUp);
            newExcutivesVersionModel.setRegDate(new Date());
            newExcutivesVersionModel.setFileHashData(fileHashUtil.setFileName(dbFileName)); // 해쉬값 set

            excutivesVersionRepository.save(newExcutivesVersionModel);

            makeDataResponseModel.setOrgData(newExcutivesVersionModel);

        } catch (NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return makeDataResponseModel;
    }

    @Override
    public MakeDataResponseModel updateAppVersion(AppVersionModel requestApp) throws Exception {
        MakeDataResponseModel makeDataResponseModel = new MakeDataResponseModel();
        List<AppVersionModel> appVersionModelList = appVersionRepository.findByDeviceOsTypeOrderByAppSeqDesc(requestApp.getDeviceOsType());

        AppVersionModel appVersion = appVersionModelList.size() > 0 ? appVersionModelList.get(0) : new AppVersionModel();
        AppVersionModel newAppVersionModel = new AppVersionModel();

        try {
            newAppVersionModel.setAppVersionSize(appVersionModelList.size()); // APP size set

            newAppVersionModel.setAppVersion(appVersion.getAppVersion());
            newAppVersionModel.setDeviceOsType(requestApp.getDeviceOsType());
            newAppVersionModel.setUpdateMessage(requestApp.getUpdateMessage());
            newAppVersionModel.setAppFileName(requestApp.getAppFileName());
            newAppVersionModel.setForceUpdate(requestApp.getForceUpdate());
            newAppVersionModel.setRegDate(new Date());

            appVersionRepository.save(newAppVersionModel);

            makeDataResponseModel.setAppData(newAppVersionModel);

        } catch (NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return makeDataResponseModel;
    }

    // 모바일 Test 용
    @Override
    public AppVersionModel updateAppVersion(String osType, String forceUp, String noticeMsg) throws Exception {
        List<AppVersionModel> appVersionModelList = appVersionRepository.findByDeviceOsTypeOrderByAppSeqDesc(osType);

        AppVersionModel appVersion = appVersionModelList.size() > 0 ? appVersionModelList.get(0) : new AppVersionModel();

        AppVersionModel newAppVersionModel = new AppVersionModel();
        try {
            newAppVersionModel.setAppVersionSize(appVersionModelList.size());
            newAppVersionModel.setDeviceOsType(osType);
            newAppVersionModel.setAppVersion(appVersion.getAppVersion());
            newAppVersionModel.setUpdateMessage(appVersion.getUpdateMessage());
            newAppVersionModel.setAppFileName(appVersion.getAppFileName());
            newAppVersionModel.setForceUpdate(forceUp);
            newAppVersionModel.setRegDate(new Date());

            appVersionRepository.save(newAppVersionModel);

        } catch (NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return newAppVersionModel;
    }

    // 모바일 Test 용
    @Override
    public ExcutivesVersionModel updateOrgVersion(String forceUp, String comp) throws Exception {
        List<ExcutivesVersionModel> excutivesMaxVersionList = excutivesVersionRepository.findByCompOrderBySeqDesc(comp);
        ExcutivesVersionModel newExcutivesVersionModel = new ExcutivesVersionModel();

        try {
            ExcutivesVersionModel excutivesMaxVersion = excutivesMaxVersionList.get(0);

            newExcutivesVersionModel.setExcutivesVersionSize(excutivesMaxVersionList.size());

            newExcutivesVersionModel.setExtOrgCompany(false); // json 버전 2개 였으나 하나로 합침

            newExcutivesVersionModel.setExcutivesMajorVersion(excutivesMaxVersion.getExcutivesMajorVersion());
            newExcutivesVersionModel.setExcutivesMinorVersion(excutivesMaxVersion.getExcutivesMinorVersion());
            newExcutivesVersionModel.setJsonFilename(excutivesMaxVersion.getJsonFilename(), excutivesMaxVersion.getJsonFilename());
            newExcutivesVersionModel.setExtJsonFilename(excutivesMaxVersion.getExtJsonFilename(), excutivesMaxVersion.getExtJsonFilename());
            newExcutivesVersionModel.setDeviceOsType(excutivesMaxVersion.getDeviceOsType());
            newExcutivesVersionModel.setComp(comp);
            newExcutivesVersionModel.setForceUpdate(forceUp);
            newExcutivesVersionModel.setRegDate(new Date());

            excutivesVersionRepository.save(newExcutivesVersionModel);

        } catch (NullPointerException ex) {
            throw new NullPointerException(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return newExcutivesVersionModel;
    }

    @Override
    public String setNotice(String updateNoticeCode, String osType, String msg, String useYn) throws Exception {

        List<AppVersionModel> appVersionList = appVersionRepository.findByDeviceOsTypeOrderByAppSeqDesc(osType);
        AppVersionNoticeModel newAppVersionNotice = new AppVersionNoticeModel();
        AppVersionModel appVersion = appVersionList.get(0);

        newAppVersionNotice.setAppSeq(appVersion.getAppSeq());
        newAppVersionNotice.setNoticeMessage(!msg.equals("") ? msg : "");
        newAppVersionNotice.setUseYn(useYn);
        newAppVersionNotice.setForceUpdate(updateNoticeCode.equals(CommonValue.NOTICE_FORCE_CODE) ? "Y" : "N");
        newAppVersionNotice.setRegDate(new Date());

        appVersionNoticeRepository.save(newAppVersionNotice);

        return CommonValue.SUCCESS_CODE;
    }

    @Override
    @Transactional
    public MakeDataResponseModel changeTelNo() {
        MakeDataResponseModel makeDataResponseModel = new MakeDataResponseModel();

        List<OrgEmpInfoModel> OrgEmpInfoResult = orgEmpInfoRepository.findAll();

        String empId = "";
        String empName = "";

        try {
            for (OrgEmpInfoModel empInfo : OrgEmpInfoResult) {

                empId = empInfo.getEmpId();
                empName = empInfo.getEmpName();

                //공백 제거
                String trimMobile = empInfo.getMobileTelno().replaceAll("\\p{Z}", "");
                String trimOffice = empInfo.getOfficeTelno().replaceAll("\\p{Z}", "");

                // 번호 유효 여부 체크
                boolean mobileMatch = empInfo.getChkPattern(trimMobile);
                boolean officeMatch = empInfo.getChkPattern(trimOffice);

                if (mobileMatch) {
                    empInfo.setMobileTelno(trimMobile);
                    empInfo.setMobileChk("Y");
                } else {
                    // 다중번호 체크
                    String mobileNum = empInfo.getChkMultiPatternReturnNum(trimMobile);

                    // 다중번호 -> 단일번호 변환 여부
                    if (mobileNum.equals(trimMobile)) {
                        empInfo.setMobileChk("N");
                    } else {
                        empInfo.setMobileTelno(mobileNum);
                        empInfo.setMobileChk("Y");
                    }
                }

                if (officeMatch) {
                    empInfo.setOfficeTelno(trimOffice);
                    empInfo.setOfficeChk("Y");
                } else {
                    // 다중번호 체크
                    String officeNum = empInfo.getChkMultiPatternReturnNum(trimOffice);

                    // 다중번호 -> 단일번호 변환 여부
                    if (officeNum.equals(trimOffice)) {
                        empInfo.setOfficeChk("N");
                    } else {
                        empInfo.setOfficeTelno(officeNum);
                        empInfo.setOfficeChk("Y");
                    }
                }

            }
        } catch (NullPointerException ex) {
            makeDataResponseModel.setReturnCode(CommonValue.ERROR_EXCEPTION_CODE);
            makeDataResponseModel.setReturnMessage(ex.getMessage());
        } finally {
            log.info("OrgEmpInfoResult =====> " + OrgEmpInfoResult.size());
            log.info("getEmpName =====> " + empName);
            log.info("getEmpId =====> " + empId);
        }

        makeDataResponseModel.setReturnCode(CommonValue.SUCCESS_CODE);
        makeDataResponseModel.setReturnMessage(CommonValue.SUCCESS_MESSAGE);

        return makeDataResponseModel;
    }
}

