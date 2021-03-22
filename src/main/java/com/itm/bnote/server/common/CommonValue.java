package com.itm.bnote.server.common;

public class CommonValue {
    // AES256 키값
    public static final String AES256_KEY_VALUE = "gsitmgsitmgsitmgsitmgsitmgsitmgs";

    // AES256 키값
    public static final String AES256_KEY_VALUE2 = "gscaltexgscaltexgscaltex";

    // 성공
    public static final String SUCCESS_CODE = "0000";
    public static final String SUCCESS_MESSAGE = "SUCCESS";

    // exception 실패
    public static final String ERROR_EXCEPTION_CODE = "9999";
    public static final String ERROR_EXCEPTION_MESSAGE = "서버에서 오류가 발생했습니다.";

    // uuid 에러
    public static final String ERROR_UUID_CODE = "9001";
    public static final String ERROR_UUID_MESSAGE = "UUID값이 잘못되었습니다.";

    // RSA 키값 에러
    public static final String ERROR_RSA_CODE = "9002";
    public static final String ERROR_RSA_MESSAGE = "공개키값 오류입니다.";

    // CommonRequestModel 필수값 체크
    public static final String ERROR_CHECK_COMMONREQUEST_CODE = "9003";
    public static final String ERROR_CHECK_COMMONREQUEST_MESSAGE = "요청 필수값 오류입니다.";

    //
    public static final String ERROR_EXCUTEIVE_NULL_CODE = "9004";
    public static final String ERROR_EXCUTEIVE_NULL_MESSAGE = "해당 앱 버전에 맞는 임직원 정보 테이블 버전이 없습니다.";

    // 토피스 I/F 오류
    public static final String ERROR_TOFFICE_API_MESSAGE = "API 통신중 오류가 발생하였습니다.";

    // APP 선택 업데이트
    public static final String APP_NORMAL_UPDATE_CODE = "2000";
    public static final String APP_NORMAL_UPDATE_MESSAGE = "App 업데이트가 있습니다.\n최신 버전을 설치하시겠습니까?";

    // APP 강제  업데이트
    public static final String APP_FORCE_UPDATE_CODE = "2001";
    public static final String APP_FORCE_UPDATE_MESSAGE = "App 업데이트가 있습니다.\n최신 버전을 설치합니다.";

    // 조직도 선택 업데이트
    public static final String ORG_NORMAL_UPDATE_CODE = "1000";
    public static final String ORG_NORMAL_UPDATE_MESSAGE = "최신조직도가 존재합니다.\n조직도 업데이트를 하시겠습니까?";

    //조직도 강제 업데이트
    public static final String ORG_FORCE_UPDATE_CODE = "1001";
    public static final String ORG_FORCE_UPDATE_MESSAGE = "최신조직도가 존재합니다.\n조직도 업데이트를 진행합니다.";

    // 공지사항 코드 조회
    public static final String CONFIG_NOTICE_CODE="NT";
    public static final String NOTICE_NORMAL_CODE="9000";
    public static final String NOTICE_FORCE_CODE="9001";

    // 회사 구분 값
    public static final String ALL_COMPANY = "A";
    public static final String ITM_COMPANY = "IT";
    public static final String EXT_COMPANY = "U";

    // 개인주소록 API 호출 파라미터 암호화 KEY 값
    public static final String IF_SECURITY_KEY ="p6z3OQIwgpGz0GodttjyCA==";
    public static final String IF_IV = "TOFFICE";

    // 패턴 치환문자 코드 조회
    public static final String CONFIG_ALT_STRING_CODE="ALT";
    public static final String CONFIG_PATTERN_STRING_CODE="PAT";

    // 모바일앱스토어 API I/F 아이디
    public static final String IF_MOBILE_APPSTORE_ID="IF-0016";

}
