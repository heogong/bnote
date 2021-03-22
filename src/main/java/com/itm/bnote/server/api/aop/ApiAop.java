package com.itm.bnote.server.api.aop;

import com.itm.bnote.server.api.model.AppVersionModel;
import com.itm.bnote.server.api.model.AppVersionNoticeModel;
import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.api.model.CommonResponseModel;
import com.itm.bnote.server.api.repository.AppVersionRepository;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.privateAddress.model.PrivateAddressModel;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
@Slf4j
public class ApiAop {

    @Autowired
    private AppVersionRepository appVersionRepository;


    /**
     * @Date 2019.05.30
     * @Comment :
     * ApiController.checkVersion 호출 파라미터 로그 기록
     * ApiController.checkVersion 진행 후 OS별 공지사항 여부 확인 - (공지사항 필요여부 확인)
     */
    @Around("execution(* com.itm.bnote.server.api.controller.ApiController.checkVersion(..))")
    public CommonResponseModel checkNoticeConfig(ProceedingJoinPoint joinPoint) throws Throwable {

        // api 로그 기록
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] signatureArgs = joinPoint.getArgs();

        for (Object signatureArg : signatureArgs) {
            log.info(signature.getName() + " - " + signatureArg.toString());
        }
        // api 로그 기록

        // 공지사항 여부 확인
        CommonRequestModel commonRequestModel = (CommonRequestModel) joinPoint.getArgs()[0]; // 호출 데이터
        CommonResponseModel commonResponseModel = (CommonResponseModel) joinPoint.proceed(); // 리턴 데이터

        List<AppVersionModel> appVersionList = appVersionRepository.findByDeviceOsTypeOrderByAppSeqDesc(commonRequestModel.getDeviceOsType());

        if (appVersionList.size() > 0) {
            List<AppVersionNoticeModel> appVersionNoticeList = appVersionList.get(0).getNoticeModel();
            if (appVersionNoticeList.size() > 0) {
                if (appVersionNoticeList.get(0).getUseYn().equals("Y")) {
                    commonResponseModel.setNoticeCode(appVersionNoticeList.get(0).getForceUpdate().equals("Y") ? CommonValue.NOTICE_FORCE_CODE : CommonValue.NOTICE_NORMAL_CODE);
                    commonResponseModel.setNoticeMessage(appVersionNoticeList.get(0).getNoticeMessage());
                }
            }
        }

        return commonResponseModel;
    }

    /**
     * @Date 2019.07.03
     * @Comment :
     * PrivateAddressController 로그 기록
     */
    @Before("execution(* com.itm.bnote.server.api.controller.PrivateAddressController.*(..))")
    public void requestApiLog(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Object[] signatureArgs = joinPoint.getArgs();

        for (Object signatureArg : signatureArgs) {
            log.info(signature.getName() + " - " + signatureArg.toString());
        }

    }
}
