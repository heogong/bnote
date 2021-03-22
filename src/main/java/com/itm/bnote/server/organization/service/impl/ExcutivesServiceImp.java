package com.itm.bnote.server.organization.service.impl;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.MakeEncryFile;
import com.itm.bnote.server.common.MakeJsonObj;
import com.itm.bnote.server.common.model.MakeDataResponseModel;
import com.itm.bnote.server.configuration.AES256Util;
import com.itm.bnote.server.configuration.FileUtil;
import com.itm.bnote.server.organization.repository.OrgEmpInfoRepository;
import com.itm.bnote.server.organization.service.ExcutivesService;
import com.itm.bnote.server.selfProgress.service.SelfProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ExcutivesServiceImp<E> implements ExcutivesService {

    // 생성 파일 경로
    @Value("${file.path.orgListSavePath}")
    String filePathOrgListSavePath;

    // 생성할 파일 prefix
    @Value("${file.prefix.organization}")
    String filePrefixOrganization;

    // 회사 구분
    @Value("${company.check.ITM}")
    String companyCheckITM;

    // 조직도 업데이트 api url
    @Value("${itm.domain.url}")
    String itmDomainUrl;


    @Autowired
    private SelfProgressService selfProgressService;

    @Autowired
    private OrgEmpInfoRepository orgEmpInfoRepository;

    @Autowired
    AES256Util aes256Util;

    @Autowired
    FileUtil fileUtil;

    @Override
    @Transactional
    public MakeDataResponseModel makeExcutivesJson(List empInfoResult, String company, String forceUp) throws Exception {

        // 각 데이터 json 생성
//        MakeJsonObj<OrgEmpInfoModel> makeEXTJsonObj = new MakeJsonObj<>("EXTOrgInformation", orgEmpInfoResult);
        MakeJsonObj<E> makeEXTJsonObj = new MakeJsonObj<>("EXTOrgInformation", empInfoResult);
        makeEXTJsonObj.makeJsonObject();

        // json 데이터 암호화 및 파일 생성
        MakeEncryFile makeEncryFile = new MakeEncryFile(makeEXTJsonObj.getJsonObject());
        makeEncryFile.setDefaultInfo(company + filePrefixOrganization, filePathOrgListSavePath);
        makeEncryFile.makeData();

        MultiValueMap<String, String> resultMap = returnResult(makeEncryFile.getResultFileName(), forceUp, company);
        resultMap.add("extOrgCompany", "false");

        // 조직도 버전 업 호출
        return selfProgressService.updateExcutivesVersion(resultMap); // 결과 표시 용도
    }

    public MultiValueMap<String, String> returnResult(String fileName, String forceUpdate, String company) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map.add("osType", "and");
        map.add("dbFileName", fileName);
        map.add("forceUp", forceUpdate);
        map.add("resultMessage", CommonValue.SUCCESS_MESSAGE);
        map.add("company", company); // 회사 구분 - ALL : GS, IT : ITM

        return map;
    }
}
