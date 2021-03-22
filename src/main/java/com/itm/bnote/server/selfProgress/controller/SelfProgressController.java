package com.itm.bnote.server.selfProgress.controller;

import com.itm.bnote.server.api.model.AppVersionModel;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.model.MakeDataResponseModel;
import com.itm.bnote.server.selfProgress.model.SelfProgressResponseModel;
import com.itm.bnote.server.selfProgress.service.SelfProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController // @Controller + @ResponseBody
@CrossOrigin(origins = "*")
@Transactional
@RequestMapping("/gsITMSelfProgress")
public class SelfProgressController {

    @Autowired
    private SelfProgressService selfProgressService;

    @PostMapping(value = "/updateExcutivesVersion")
    public MakeDataResponseModel updateExcutivesVersion(
            @RequestParam(defaultValue = "and") String osType,
            @RequestParam(required = true) String dbFileName,
            @RequestParam(defaultValue = "N") String forceUp) throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map.add("osType", osType);
        map.add("dbFileName", dbFileName);
        map.add("forceUp", forceUp);
        map.add("extOrgCompany", "false"); // 외부 조직도 업데이트 여부 - 하나의 컬럼으로만 조직도를 url을 제공 하므로 분기 필요 없음

        return selfProgressService.updateExcutivesVersion(map);
    }

    @GetMapping(value = "/updateAppVersion")
    public MakeDataResponseModel updateAppVersion(@ModelAttribute @Valid AppVersionModel appVersionModel, BindingResult bindingResult) throws Exception {

        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            return selfProgressService.updateAppVersion(appVersionModel);
        }
    }

    /**
     * @Date 2019.05.28
     * @Comment :
     * 테스트 용
     * updateType : BOTH - APP/조직도 버전업, APP - APP 버전업, ORG - 조직도 버전업
     * osType : and, ios
     * forceUp : Y, N - APP 강제 업데이트 여부
     */
    @GetMapping(value = "/updateVersion")
    public SelfProgressResponseModel updateVersion(
            @RequestParam(defaultValue = "ORG", required = true) String updateType,
            @RequestParam(defaultValue = "and") String osType,
            @RequestParam(defaultValue = "N") String forceUp,
            @RequestParam(defaultValue = "") String noticeMsg) throws Exception {

        SelfProgressResponseModel selfProgressResponseModel = new SelfProgressResponseModel();

        selfProgressResponseModel.setAppVersion(Arrays.asList("BOTH", "APP").contains(updateType) ? selfProgressService.updateAppVersion(osType, forceUp, noticeMsg).getAppVersion() : 0);
        selfProgressResponseModel.setExcutivesVersion(Arrays.asList("BOTH", "ORG").contains(updateType) ? selfProgressService.updateOrgVersion(forceUp, CommonValue.ITM_COMPANY).getExcutivesMinorVersion() : 0);
        selfProgressResponseModel.setExcutivesVersion(Arrays.asList("BOTH", "ORG").contains(updateType) ? selfProgressService.updateOrgVersion(forceUp, CommonValue.ALL_COMPANY).getExcutivesMinorVersion() : 0);
        selfProgressResponseModel.setReturnMessage(updateType);
        selfProgressResponseModel.setReturnCode(CommonValue.SUCCESS_CODE);
        selfProgressResponseModel.setAppNoticeMsg(noticeMsg);

        return selfProgressResponseModel;
    }

    /**
     * @Date 2019.05.31
     * @Comment :
     * 공지사항 설정
     * updateType : and / ios,
     * noticeType : NORMAL - 선택 공지, FORCE - 강제공지
     * msg : 공지사항 메세지
     * useYn : Y, N
     */
    @PostMapping(value = "/setNotice")
    public SelfProgressResponseModel setNotice(
            @RequestParam(defaultValue = "and", required = true) String osType,
            @RequestParam(defaultValue = "NORMAL") String noticeType,
            @RequestParam(defaultValue = "") String msg,
            @RequestParam(defaultValue = "N") String useYn) throws Exception {

        SelfProgressResponseModel selfProgressResponseModel = new SelfProgressResponseModel();

        String noticeCode = selfProgressResponseModel.getNoticeCode(noticeType);

        selfProgressResponseModel.setReturnCode(selfProgressService.setNotice(noticeCode, osType, msg, useYn));

        selfProgressResponseModel.setReturnMessage(osType);
        selfProgressResponseModel.setNoticeYn(useYn);
        selfProgressResponseModel.setNoticeCode(noticeCode);

        return selfProgressResponseModel;
    }

    @GetMapping(value = "/changeTelNo")
    public MakeDataResponseModel changeTelNo() throws Exception {

        return selfProgressService.changeTelNo();

    }
}
