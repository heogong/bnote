package com.itm.bnote.server.coPhone.controller;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.api.model.CommonResponseModel;
import com.itm.bnote.server.api.service.ApiService;
import com.itm.bnote.server.coPhone.model.ChonePhoneRequestModel;
import com.itm.bnote.server.coPhone.model.ChonePhoneResponseModel;
import com.itm.bnote.server.coPhone.service.ChonePhoneService;
import com.itm.bnote.server.common.CommonValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController // @Controller + @ResponseBody
@CrossOrigin(origins = "*")
@RequestMapping("/ITMCallServer/gsITMExcutivesApi")
public class ChonePhoneController {


    @Autowired
    private ApiService apiService;


    @Autowired
    private ChonePhoneService choPhoneService;

    // 어플리케이션 다운로드 경로
    @Value("${file.url.buildfileDownLoadPath}")
    String fileUrlBuildFileDownLoadPath;


    @PostMapping(value = "/checkAppVersion")
    public ChonePhoneResponseModel checkAppVersion(@RequestBody @Valid ChonePhoneRequestModel chonePhoneRequestModel, BindingResult bindingResult) throws Exception {

        ChonePhoneResponseModel cpCommonResponseModel = new ChonePhoneResponseModel();
        CommonResponseModel commonResponseModel = new CommonResponseModel();

        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppPackageName(chonePhoneRequestModel.getAppPackageName());
        commonRequestModel.setAppVersion(chonePhoneRequestModel.getAppVersion());
        commonRequestModel.setDeviceOsType(chonePhoneRequestModel.getDeviceOsType());
        commonRequestModel.setDeviceOsVersion(chonePhoneRequestModel.getDeviceOsVersion());
        commonRequestModel.setRsaKeyValue(chonePhoneRequestModel.getRsaKeyValue());
        commonRequestModel.setExcutivesVersion(chonePhoneRequestModel.getExcutivesVersion());
        commonRequestModel.setDeviceUuid(chonePhoneRequestModel.getDeviceUuid());
        commonRequestModel.setExcutivesMajorVersion(chonePhoneRequestModel.getExcutivesMajorVersion());
        commonRequestModel.setDeviceModel(chonePhoneRequestModel.getDeviceModel());

        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            commonResponseModel = apiService.checkDeviceUuid(commonRequestModel);

            if (commonResponseModel.getReturnCode().equals(CommonValue.SUCCESS_CODE)) {
                cpCommonResponseModel = choPhoneService.checkAppVersion(commonRequestModel);

                cpCommonResponseModel.setReturnCode(commonResponseModel.getReturnCode());
                cpCommonResponseModel.setReturnMessage(commonResponseModel.getReturnMessage());
                cpCommonResponseModel.setDeviceUuid(commonResponseModel.getDeviceUuid());

            }
        }
        return cpCommonResponseModel;
    }


    @PostMapping(value = "/checkExcutivesVersion")
    public ChonePhoneResponseModel checkExcutivesVersion(@RequestBody @Valid ChonePhoneRequestModel chonePhoneRequestModel, BindingResult bindingResult) throws Exception {

        ChonePhoneResponseModel cpCommonResponseModel = new ChonePhoneResponseModel();
        CommonResponseModel commonResponseModel = new CommonResponseModel();

        CommonRequestModel commonRequestModel = new CommonRequestModel();
        commonRequestModel.setAppPackageName(chonePhoneRequestModel.getAppPackageName());
        commonRequestModel.setAppVersion(chonePhoneRequestModel.getAppVersion());
        commonRequestModel.setDeviceOsType(chonePhoneRequestModel.getDeviceOsType());
        commonRequestModel.setDeviceOsVersion(chonePhoneRequestModel.getDeviceOsVersion());
        commonRequestModel.setRsaKeyValue(chonePhoneRequestModel.getRsaKeyValue());
        commonRequestModel.setExcutivesVersion(chonePhoneRequestModel.getExcutivesVersion());
        commonRequestModel.setDeviceUuid(chonePhoneRequestModel.getDeviceUuid());
        commonRequestModel.setExcutivesMajorVersion(chonePhoneRequestModel.getExcutivesMajorVersion());
        commonRequestModel.setDeviceModel(chonePhoneRequestModel.getDeviceModel());

        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            commonResponseModel = apiService.checkDeviceUuid(commonRequestModel);

            if (commonResponseModel.getReturnCode().equals(CommonValue.SUCCESS_CODE)) {
                cpCommonResponseModel = choPhoneService.checkExcutivesVersion(commonRequestModel);

                cpCommonResponseModel.setReturnCode(commonResponseModel.getReturnCode());
                cpCommonResponseModel.setReturnMessage(commonResponseModel.getReturnMessage());
                cpCommonResponseModel.setDeviceUuid(commonResponseModel.getDeviceUuid());
            }
        }
        return cpCommonResponseModel;
    }

}
