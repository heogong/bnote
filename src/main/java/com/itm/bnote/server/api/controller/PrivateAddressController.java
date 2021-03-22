package com.itm.bnote.server.api.controller;

import com.itm.bnote.server.common.model.CommonRequireRequestModel;
import com.itm.bnote.server.common.model.CommonReturnModel;
import com.itm.bnote.server.privateAddress.model.PrivateAddressModel;
import com.itm.bnote.server.privateAddress.service.PrivateAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController // @Controller + @ResponseBody
@CrossOrigin(origins = "*")
@RequestMapping("/PrivateAddressApi")
@Component
public class PrivateAddressController {

    @Autowired
    private PrivateAddressService privateAddressService;

    /**
     * @Date 2019.06.28 privateAddressModel
     * @Comment : 주소록 등록
     */
//    @PostMapping(value = "/addPrivateAddress", produces = "text/plain;charset=UTF-8")
    @PostMapping(value = "/addPrivateAddress")
    public CommonReturnModel addPrivateAddress(@RequestBody @Valid PrivateAddressModel privateAddressModel, BindingResult bindingResult) throws Exception {
        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            return privateAddressService.addPrivateAddress(privateAddressModel);
        }
    }

    /**
     * @Date 2019.07.16 privateAddressModel
     * @Comment : 주소록 등록
     * TEST 후 addPrivateAddress 메소드명 변경
     */
    //    @PostMapping(value = "/testAddPrivateAddress", produces = "text/plain;charset=UTF-8")
    @PostMapping(value = "/testAddPrivateAddress")
    public CommonReturnModel testAddPrivateAddress(
            @RequestPart @ModelAttribute @Valid PrivateAddressModel privateAddressModel,
            BindingResult bindingResult) throws Exception {

        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            return privateAddressService.addPrivateAddress2(privateAddressModel);
        }
    }


    /**
     * @Date 2019.07.29
     * @Comment : 명함파싱 문자열
     *
     */
    @PostMapping(value="/getParseString")
    public Map<String, Object> parseString(@RequestBody @Valid CommonRequireRequestModel commonRequireRequestModel, BindingResult bindingResult) throws Exception {

        // 필수값 체크
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        } else {
            return privateAddressService.getParseString();
        }
    }
}
