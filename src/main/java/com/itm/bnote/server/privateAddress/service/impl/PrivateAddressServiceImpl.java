package com.itm.bnote.server.privateAddress.service.impl;

import com.itm.bnote.server.common.CallRestApi;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.MakeCardImgFile;
import com.itm.bnote.server.common.exception.PrivateAddressException;
import com.itm.bnote.server.common.model.CommonReturnModel;
import com.itm.bnote.server.privateAddress.model.PrivateAddressModel;
import com.itm.bnote.server.privateAddress.service.PrivateAddressService;
import com.itm.bnote.server.selfProgress.model.CodeInfoModel;
import com.itm.bnote.server.selfProgress.repository.CodeInfoRepository;
import com.lgcns.ikep.common.security.SecurityEncrypter;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PrivateAddressServiceImpl implements PrivateAddressService {

    @Value("${privateAddress.add}")
    String privateAddressAddUrl;

    @Value("${file.url.cardImgSavePath}")
    String cardImgSaveUrl;

    @Value("${file.path.cardImgSavePath}")
    String cardImgSavePath;

    @Autowired
    private CodeInfoRepository codeInfoRepository;


    @Override
    public CommonReturnModel addPrivateAddress2(PrivateAddressModel privateAddressModel) {
        CommonReturnModel commonReturnModel = new CommonReturnModel();

        // 필수값 userInfo 생성
        Date date = new Date();
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

        try {
            SecurityEncrypter security = new SecurityEncrypter(CommonValue.IF_SECURITY_KEY, CommonValue.IF_IV);
            String data = "ID="+privateAddressModel.getUserId()+",EMAIL="+privateAddressModel.getUserEmail()+",DAY="+yyyyMMdd.format(date);
            String userInfo = URLEncoder.encode(security.encrypt(data), "UTF-8");

            // 파라미터 생성
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

            map.add("cid", privateAddressModel.getCid());
            map.add("systemid", privateAddressModel.getSystemId());
            map.add("userInfo", userInfo);
            map.add("propFullName", privateAddressModel.getPropFullName());
            map.add("propEmail1", privateAddressModel.getPropEmail1());
            map.add("propCell1", privateAddressModel.getPropCell1());
            map.add("workTel1", privateAddressModel.getWorkTel1());
            map.add("propOrg1", privateAddressModel.getPropOrg1());
            map.add("propOrg2", privateAddressModel.getPropOrg2());
            map.add("propTitle", privateAddressModel.getPropTitle());
            map.add("homeTel1", privateAddressModel.getHomeTel1());
            map.add("workFax1", privateAddressModel.getWorkFax1());
            map.add("addr", privateAddressModel.getAddr());
            map.add("propUrl", privateAddressModel.getPropUrl());
            map.add("cardUid", privateAddressModel.getCardUid());
            map.add("grpUid", privateAddressModel.getGrpUid());
            map.add("targetGrpUid", privateAddressModel.getTargetGrpUid());
            map.add("grpName", privateAddressModel.getGrpName());
            map.add("note", privateAddressModel.getNote());

            // 명함이미지 저장
            MakeCardImgFile makeCardImgFile = new MakeCardImgFile(privateAddressModel.getCardImg(), cardImgSavePath);
            // 명함이미지 url api 전송
            map.add("cardImgUrl", cardImgSaveUrl + makeCardImgFile.getCardImgFileName());

            CallRestApi callRestApi = new CallRestApi(privateAddressAddUrl, map);
            callRestApi.call();

            JSONObject resultJSONObject = callRestApi.result();

            commonReturnModel.setReturnMessage(String.valueOf(resultJSONObject.get("errMsg")));
            commonReturnModel.setReturnCode((boolean) resultJSONObject.get("result") ? CommonValue.SUCCESS_CODE : CommonValue.ERROR_EXCEPTION_CODE);

        } catch(Exception e) {
            throw new PrivateAddressException(CommonValue.ERROR_TOFFICE_API_MESSAGE);
        }

        return commonReturnModel;
    }

    // TEST 완료 후 삭제
    @Override
    public CommonReturnModel addPrivateAddress(PrivateAddressModel privateAddressModel) throws Exception {
        CommonReturnModel commonReturnModel = new CommonReturnModel();

        // 필수값 userInfo 생성
        Date date = new Date();
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

        try {
            SecurityEncrypter security = new SecurityEncrypter(CommonValue.IF_SECURITY_KEY, CommonValue.IF_IV);
            String data = "ID="+privateAddressModel.getUserId()+",EMAIL="+privateAddressModel.getUserEmail()+",DAY="+yyyyMMdd.format(date);
            String userInfo = URLEncoder.encode(security.encrypt(data), "UTF-8");

            // 파라미터 생성
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

            map.add("cid", privateAddressModel.getCid());
            map.add("systemid", privateAddressModel.getSystemId());
            map.add("userInfo", userInfo);
            map.add("propFullName", privateAddressModel.getPropFullName());
            map.add("propEmail1", privateAddressModel.getPropEmail1());
            map.add("propCell1", privateAddressModel.getPropCell1());
            map.add("workTel1", privateAddressModel.getWorkTel1());
            map.add("propOrg1", privateAddressModel.getPropOrg1());
            map.add("propOrg2", privateAddressModel.getPropOrg2());
            map.add("propTitle", privateAddressModel.getPropTitle());
            map.add("homeTel1", privateAddressModel.getHomeTel1());
            map.add("workFax1", privateAddressModel.getWorkFax1());
            map.add("addr", privateAddressModel.getAddr());
            map.add("propUrl", privateAddressModel.getPropUrl());
            map.add("cardUid", privateAddressModel.getCardUid());
            map.add("grpUid", privateAddressModel.getGrpUid());
            map.add("targetGrpUid", privateAddressModel.getTargetGrpUid());
            map.add("grpName", privateAddressModel.getGrpName());
            map.add("note", privateAddressModel.getNote());

            CallRestApi callRestApi = new CallRestApi(privateAddressAddUrl, map);
            callRestApi.call();

            JSONObject resultJSONObject = callRestApi.result();

            commonReturnModel.setReturnMessage(String.valueOf(resultJSONObject.get("errMsg")));
            commonReturnModel.setReturnCode((boolean) resultJSONObject.get("result") ? CommonValue.SUCCESS_CODE : CommonValue.ERROR_EXCEPTION_CODE);

        } catch(Exception e) {
            throw new PrivateAddressException(CommonValue.ERROR_TOFFICE_API_MESSAGE);
        }

        return commonReturnModel;
    }

    @Override
    public Map<String, Object> getParseString() {

        Map<String, String> altMap = new HashMap<>();
        Map<String, List<String>> patternMap = new HashMap<>();
        Map<String, Object> rootMap = new HashMap<>();

        // 공통 코드테이블 조회 CODE 값
        String altStringCode = CommonValue.CONFIG_ALT_STRING_CODE;
        String patternStringCode = CommonValue.CONFIG_PATTERN_STRING_CODE;

        List<CodeInfoModel> codeALTList = codeInfoRepository.findByCodeGrp(altStringCode); // altString 조회
        List<?> codePatternNmList = codeInfoRepository.findByPatternNameInfo(patternStringCode); // pattern 조회

        // SET altString 데이터
        for(CodeInfoModel codeInfo : codeALTList) {
            altMap.put(codeInfo.getCodeNm(), codeInfo.getCodeValue());
        }

        // SET pattern 데이터
        for(Object codeNm : codePatternNmList) {
            // 코드 VALUE 조회(List)
            List<?> codeValueList = codeInfoRepository.findByPatternValueInfo(patternStringCode, codeNm.toString());
            patternMap.put(codeNm.toString(), (List<String>) codeValueList);
        }

        // SET 리턴 데이터
        rootMap.put("altString", altMap);
        rootMap.put("pattern", patternMap);

        return rootMap;
    }
}
