package com.itm.bnote.server.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("CommonUtil")
public class CommonUtil {


    // UUID 생성
    public String createUuid() {

        UUID uuid = UUID.randomUUID();

        return uuid.toString();

    }

    // null, 빈값, ""값 체크
    public boolean isNullorEmpty(String str) {

        boolean result = true;

        if(str.isEmpty() || str.equals("") || str == null || str.equals("null")) {
            result = false;
        }

        return result;
    }

    // null, 빈값, ""값 체크
    public boolean isNullorEmpty(double dob) {

        boolean result = true;

        if(dob == 0) {
            result = false;
        }

        return result;
    }
}
