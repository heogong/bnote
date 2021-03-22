package com.itm.bnote.server.api.controller;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.api.model.CommonResponseModel;
import com.itm.bnote.server.api.service.ApiService;
import com.itm.bnote.server.mobileAppstore.service.MobileAppstoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController // @Controller + @ResponseBody
@CrossOrigin(origins = "*")
@RequestMapping("/OrganizationApi")
@Component
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private MobileAppstoreService mobileAppstoreService;

    /**
     * @Date 2019.05.30
     * @Comment : DEVICE UUID / 조직도 / APP 버전 체크
     * <p>
     * aop 설정 된 controller
     */
    @PostMapping(value = "/checkVersion")
    public CommonResponseModel checkVersion(@RequestBody @Valid CommonRequestModel commonRequestModel, BindingResult bindingResult) throws Exception {
        CommonResponseModel commonResponseModel;

        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            commonResponseModel = apiService.checkDeviceUuid(commonRequestModel); // UUID 체크
            commonResponseModel = apiService.checkExcutivesVersion(commonRequestModel, commonResponseModel); // 조직도 버전 체크
            commonResponseModel = mobileAppstoreService.APIcheckAppVersion(commonRequestModel, commonResponseModel); // 모바일 APP 버전 체크 - 모바일앱스토어 API 호출
//            commonResponseModel = apiService.checkAppVersion(commonRequestModel, commonResponseModel); // 모바일 APP 버전 체크

            return commonResponseModel;
        }
    }
}
