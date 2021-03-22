package com.itm.bnote.server.common.model;

import lombok.Data;

/**
 * @Date 2019.07.31
 * @Comment : 공통 리턴 값
 *
 */
public @Data
class CommonReturnModel {
    private String returnCode;
    private String returnMessage;
}
