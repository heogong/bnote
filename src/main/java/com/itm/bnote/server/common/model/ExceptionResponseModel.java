package com.itm.bnote.server.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

@EqualsAndHashCode(callSuper = false)
public @Data
class ExceptionResponseModel extends CommonReturnModel{
    private String returnDetailMessage;
}
