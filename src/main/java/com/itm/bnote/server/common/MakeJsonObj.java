package com.itm.bnote.server.common;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

import java.util.List;


@Slf4j
public class MakeJsonObj<E> {
    private String JSON_OBJ_NAME;

    private List<E> GROUP_DATA;

    private JSONObject jsonObjectGSITMExcutivesResult = new JSONObject();

    public MakeJsonObj(String objectName, List<E> group) {
        this.JSON_OBJ_NAME = objectName;
        this.GROUP_DATA = group;
    }

    public void makeJsonObject() throws Exception {
        int resultSize = 0;        // 임직원 정보 개수

        if (this.GROUP_DATA.size() == 0) {
            throw new Exception("임직원 목록 개수가 " + resultSize + "개입니다. 배치 종료합니다.");
        }

        try {
            log.info("===== 임직원 JSON 변환 START =====");

            jsonObjectGSITMExcutivesResult.put(this.JSON_OBJ_NAME, this.GROUP_DATA);
            jsonObjectGSITMExcutivesResult.put("ORG_VERSION", "22222222222222222222222222");

            log.info("===== 임직원 JSON 변환 END =====");

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String getJsonObject() {
        return jsonObjectGSITMExcutivesResult.toJSONString();
    }
}
