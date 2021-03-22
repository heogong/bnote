package com.itm.bnote.server.selfProgress.model;

import com.itm.bnote.server.common.CommonValue;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public @Data
class SelfProgressResponseModel {
    private String returnCode;
    private String returnMessage;
    private String appVersion;
    private String excutivesVersion;
    private String noticeYn;
    private String noticeCode;
    private String appNoticeMsg;

    public void setAppVersion(double appVersion) {
        this.appVersion = String.format("%.2f", appVersion);
    }

    public void setExcutivesVersion(double excutivesVersion) {
        this.excutivesVersion = String.format("%.2f", excutivesVersion);
    }

    public void setReturnMessage(String updateType) {
        this.returnMessage = updateObjectType.valueOf(updateType).getMessage();
    }

    public String getNoticeCode(String noticeType) {
        return updateObjectType.valueOf(noticeType).getCode();
    }

    enum updateObjectType {
        BOTH("APP / 조직도 버전 업데이트 되었습니다.", "0000"),
        APP("APP 버전 업데이트 되었습니다.", "0000"),
        ORG("조직도 버전 업데이트 되었습니다.", "0000"),
        OBOTH("안드로이드 / iOS 공지사항 업데이트 되었습니다.", "0000"),
        NORMAL("선택 공지사항 업데이트 되었습니다.", CommonValue.NOTICE_NORMAL_CODE),
        FORCE("강제 공지사항 업데이트 되었습니다.", CommonValue.NOTICE_FORCE_CODE),
        and("안드로이드 공지사항 업데이트 되었습니다.", "0000"),
        ios("iOS 공지사항 업데이트 되었습니다.", "0000");

        final private String message;
        final private String valueCode;

        // enum에서 생성자 같은 역할
        private updateObjectType(String message, String code) {
            this.message = message;
            this.valueCode = code;
        }

        // 문자를 받아오는 함수
        public String getMessage() {
            return message;
        }

        // 코드를 받아오는 함수
        public String getCode() {
            return valueCode;
        }
    }
}
