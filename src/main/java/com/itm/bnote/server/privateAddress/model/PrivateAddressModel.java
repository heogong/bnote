package com.itm.bnote.server.privateAddress.model;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.model.CommonRequireRequestModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

@EqualsAndHashCode(callSuper = false)
public @Data
class PrivateAddressModel extends CommonRequireRequestModel {

    private MultipartFile cardImg;
    private String bodyString;

    public void setBodyString(String bodyString) {
        JSONParser jsonParser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
        try {
            JSONObject jsonObj = (JSONObject) jsonParser.parse(bodyString);

            super.setAppPackageName((String) jsonObj.get("appPackageName"));
            super.setAppVersion(Double.parseDouble((String) jsonObj.get("appVersion")));
            super.setDeviceOsType((String) jsonObj.get("deviceOsType"));
            super.setDeviceOsVersion((String) jsonObj.get("deviceOsVersion"));
            this.deviceUuid = (String) jsonObj.get("deviceOsVersion");
            this.cid = (String) jsonObj.get("cid");
            this.systemId = (String) jsonObj.get("systemId");
            this.userId = (String) jsonObj.get("userId");
            this.userEmail = (String) jsonObj.get("userEmail");
            this.propFullName = (String) jsonObj.get("propFullName");
            this.propEmail1 = (String) jsonObj.get("propEmail1");
            this.propCell1 = (String) jsonObj.get("propCell1");
            this.workTel1 = (String) jsonObj.get("workTel1");
            this.propOrg1 = (String) jsonObj.get("propOrg1");
            this.propOrg2 = (String) jsonObj.get("propOrg2");
            this.propTitle = (String) jsonObj.get("propTitle");
            this.homeTel1 = (String) jsonObj.get("homeTel1");
            this.workFax1 = (String) jsonObj.get("workFax1");
            this.addr = (String) jsonObj.get("addr");
            this.propUrl = (String) jsonObj.get("propUrl");
            this.grpName = (String) jsonObj.get("grpName");
            this.note = (String) jsonObj.get("note");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String cid;        // ???????????? ?????? - GSC : C1, ????????? : T6, ???????????? : C1P3

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String userId;        // ?????? ????????? ??????

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String userEmail;        // ????????? ????????? ?????????

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String propFullName;        // ??????

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    private String propEmail1;          // ?????????

    private String deviceUuid;                // ????????? ???????????? UUID : ?????? ?????? ?????? ???????????? ????????? ???
    private String systemId = "0";        // ??????????????? - 0 : IKEP, 1 : TOFFICE
    private String day;                 // ?????? ?????? - 2018-01-15
    private String propCell1;           // ?????????
    private String workTel1;            // ????????????
    private String propOrg1;            // ??????
    private String propOrg2;            // ??????
    private String propTitle;           // ??????
    private String homeTel1;            // ?????????
    private String workFax1;            // ????????????
    private String addr;                // ????????????
    private String propUrl;             // ????????????
    private String cardUid = "-1";      // ?????????id
    private String grpUid = "-1";       // ????????? ??????id
    private String targetGrpUid = "-1"; // ????????? ????????? ??????id
    private String grpName;             // ?????????
    private String note;                // ??????

}
