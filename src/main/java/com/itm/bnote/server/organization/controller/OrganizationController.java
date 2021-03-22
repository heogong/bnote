package com.itm.bnote.server.organization.controller;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.model.MakeDataResponseModel;
import com.itm.bnote.server.organization.repository.ITMOrgEmpInfoRepository;
import com.itm.bnote.server.organization.repository.OrgEmpInfoRepository;
import com.itm.bnote.server.organization.service.ExcutivesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController // @Controller + @ResponseBody
@CrossOrigin(origins = "*")
@RequestMapping("/gsITMExcutives")
public class OrganizationController {

    @Autowired
    private ExcutivesService excutivesService;

    @Autowired
    private OrgEmpInfoRepository orgEmpInfoRepository;

    @Autowired
    private ITMOrgEmpInfoRepository ITMorgEmpInfoRepository;

    @GetMapping(value = "/makeExcutivesJson")
    public Map<String, MakeDataResponseModel> makeExcutivesJson(
            @RequestParam(defaultValue = "N") String forceUp) throws Exception {

        log.info("========== /makeGsItmExcutivesJson Start ==========");

        Map<String, MakeDataResponseModel> resultOrganization = new HashMap<>();

        // GS + ITM 결과값 합치기(UNION)
        List<Object> organizationList = new ArrayList<Object>();
        organizationList.add(orgEmpInfoRepository.findAll());
        organizationList.add(ITMorgEmpInfoRepository.findAll());

        resultOrganization.put("GSOrganization", excutivesService.makeExcutivesJson(organizationList, CommonValue.ALL_COMPANY, forceUp));
        resultOrganization.put("ITMOrganization", excutivesService.makeExcutivesJson(ITMorgEmpInfoRepository.findAll(), CommonValue.ITM_COMPANY, forceUp));

        log.info("========== /makeGsItmExcutivesJson End ==========");

        return resultOrganization;
    }
}
