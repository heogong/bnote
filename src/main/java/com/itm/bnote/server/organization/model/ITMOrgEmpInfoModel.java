package com.itm.bnote.server.organization.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.regex.Pattern;

/*
 LomBok 플러그인 설치 필요
 ITM 사용자/부서 정보
 */
@Entity
@Table(name = "TB_ITM_ORG_EMP_INFO")
public
class ITMOrgEmpInfoModel {

    @Getter
    @Setter
    @Id
    @Column(name = "seq", nullable = false)
    private int seq;            // 기본키

    @Getter
    @Setter
    @Column(name = "co_id", nullable = false)
    private String coId;            // 회사 키

    @Getter
    @Setter
    @Column(name = "co_name")
    private String coName;            // 회사이름

    @Getter
    @Setter
    @Column(name = "dept_code")
    private String deptCode;

    @Getter
    @Setter
    @Column(name = "dept_type")
    private String deptType;

    @Getter
    @Setter
    @Column(name = "dept_name")
    private String deptName;

    @Getter
    @Setter
    @Column(name = "depth")
    private int depth;            // 조직도 레벨

    @Getter
    @Setter
    @Column(name = "parent_code")
    private String parentCode;

    @Getter
    @Setter
    @Column(name = "parent_fullCode")
    private String parentFullCode;

    @Getter
    @Setter
    @Column(name = "emp_id")
    private String empId;

    @Getter
    @Setter
    @Column(name = "emp_name")
    private String empName;

    @Getter
    @Setter
    @Column(name = "position_name")
    private String positionName;

    @Getter
    @Column(name = "office_telno")
    private String officeTelno;

    @Getter
    @Column(name = "mobile_telno")
    private String mobileTelno;

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "position_code")
    private String positionCode;

    @Getter
    @Setter
    @Column(name = "title_name")
    private String titleName;

    @Getter
    @Setter
    @Column(name = "SORT_NO_INT")
    private int sortNoInt;

    @Getter
    @Setter
    @Column(name = "emp_upmu")
    private String empUpmu;

    @Getter
    @Setter
    @Column(name = "mobile_chk")
    private String mobileChk;

    @Getter
    @Setter
    @Column(name = "office_chk")
    private String officeChk;

    @Getter
    @Setter
    @Column(name = "img_url")
    private String imgUrl;

    @Getter
    @Setter
    @Column(name = "work_place_etc")
    private String workPlaceEtc;

    @Getter
    @Setter
    @Column(name = "work_floor")
    private String workFloor;

    @Getter
    @Setter
    @Column(name = "office_name")
    private String officeName;

    @Getter
    @Setter
    @Column(name = "office_telno_bak")
    private String basicOfficeTelno;

    @Getter
    @Setter
    @Column(name = "mobile_telno_bak")
    private String basicMobileTelno;

    @Transient
    private String specialCharPattern = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]"; // 특수문자 제거


    public void setOfficeTelno(String officeTelno) {
        this.officeTelno = officeTelno.length() > 0 ? returnCVNUM(officeTelno.replaceAll(this.specialCharPattern, ""), this.coId) : officeTelno;
    }

    public void setMobileTelno(String mobileTelno) {
        this.mobileTelno = mobileTelno.length() > 0 ? returnCVNUM(mobileTelno.replaceAll(this.specialCharPattern, ""), this.coId) : mobileTelno;
    }

    @Transient
    @Setter
    private String chkPattern;

    @Transient
    @Setter
    private String chkMultiPatternReturnNum;

    public String getChkMultiPatternReturnNum(String number) {

        // 사내번호 멀티1 2645-6
        String telMultlRegex1 = "^\\d{4}-\\d$";

        // 사내번호 멀티2 6050/6025
        String telMultlRegex2 = "^\\d{4}/\\d{4}$";

        // 사내번호 멀티3 5013(5014), 3948(단축:4707)
        String telMultlRegex3 = "^\\d{4}.([가-힣.:.0-9]*.)$";

        //  다중4 (061)680-2614~5
        String telMultlRegex4 = "^.(\\d{2,3}.)\\d{3,4}-\\d{3,4}~\\d$";

        //  다중5 061-680-2614-5
        String telMultlRegex5 = "^\\d{2,3}-\\d{3,4}-\\d{3,4}-\\d$";

        //  다중6 2675~5
        String telMultlRegex6 = "^\\d{4}~\\d$";

        //  다중7 061-680-2725,6
        String telMultlRegex7 = "^\\d{2,3}-\\d{3,4}-\\d{3,4},\\d{1,2}$";

        //  다중8 02-2005-1968~75
        String telMultlRegex8 = "^\\d{2,3}-\\d{3,4}-\\d{3,4}~\\d{1,2}$";

        //  다중9 680-5053,4,5
        String telMultlRegex9 = "^\\d{2,3}-\\d{3,4},\\d{1,2},\\d{1,2}$";

        //  다중10 061-680-5053--6
        String telMultlRegex10 = "^\\d{2,3}-\\d{3,4}-\\d{3,4}--\\d{1,2}$";

        //  다중11 86-316-6086740~8004
        String telMultlRegex11 = "^\\d{2,3}-\\d{3,4}-\\d{5,7}~\\d{3,4}$";

        String[] phoneSplit;

        if (Pattern.matches(telMultlRegex1, number) || Pattern.matches(telMultlRegex5, number)) {
            phoneSplit = number.split("-");
            return phoneSplit.length > 2 ? phoneSplit[0] + phoneSplit[1] + phoneSplit[2] : phoneSplit[0];
        } else if (Pattern.matches(telMultlRegex2, number)) {
            phoneSplit = number.split("/");
            return phoneSplit[0];
        } else if (Pattern.matches(telMultlRegex3, number)) {
            phoneSplit = number.split("\\(");
            return phoneSplit[0];
        } else if (Pattern.matches(telMultlRegex7, number) || Pattern.matches(telMultlRegex9, number)) {
            phoneSplit = number.split(",");
            return phoneSplit[0];
        } else if (Pattern.matches(telMultlRegex10, number)) {
            phoneSplit = number.split("--");
            return phoneSplit[0];
        } else if (Pattern.matches(telMultlRegex4, number) ||
                Pattern.matches(telMultlRegex6, number) ||
                Pattern.matches(telMultlRegex8, number) ||
                Pattern.matches(telMultlRegex11, number)) {
            phoneSplit = number.split("~");
            return phoneSplit[0];
        } else {
            return number;
        }
    }

    public Boolean getChkPattern(String number) {

        // 전화번호 체크
        String telRegex1 = "^\\d{2,3}-\\d{3,4}-\\d{4}$";

        // 하이픈 없는 번호 체크
        String telRegex2 = "^\\d{2,3}\\d{3,4}\\d{4}$";

        // 괄호 지역번호 체크
        String telRegex3 = "^.(\\d{2,3}.)\\d{3,4}-\\d{4}$";

        // 4글자 번호
        String telRegex4 = "^\\d{3,4}-\\d{4}$";

        // 외국 번호
        String telRegex5 = "^\\d{1,3}-\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // 하이픈 없는 외국번호
        String telRegex6 = "^\\d{1,3}\\d{2,3}\\d{3,4}\\d{4}$";

        // . 구분
        String telRegex7 = "^\\d{2,3}.\\d{3,4}.\\d{4}$";

        // - 하이픈 하나
        String telRegex8 = "^\\d{2,3}-\\d{3,4}\\d{4}$";

        // 외국 번호 괄호
        String telRegex9 = "^.(\\d{2,3}.)\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // ~ 구분
        String telRegex10 = "^\\d{2,3}~\\d{3,4}~\\d{4}$";

        // 외국 번호 (특이함)
        String telRegex11 = "^\\d{1,2}-\\d{2,5}-\\d{3,5}$";

        // 외국 번호 (특이함2) 86 156-1364-7111
        String telRegex12 = "^\\d{1,2}\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // 외국 번호 (특이함3) (86) 186 6226 9101
        String telRegex13 = "^.(\\d{1,2}.)\\d{2,3}\\d{3,4}\\d{3,4}$";

        // 외국 번호 (특이함4) 86-185 5112 3177
        String telRegex14 = "^\\d{1,2}-\\d{2,3}\\d{3,4}\\d{3,4}$";

        // 외국 번호 (특이함5) 420-725-386687
        String telRegex15 = "^\\d{2,3}-\\d{3,4}-\\d{3,7}$";

        // 외국 번호 (특이함6) 86-176-8583 2266
        String telRegex16 = "^\\d{1,2}-\\d{2,3}-\\d{3,4}\\d{3,4}$";

        // 국내번호 반 괄호 061)680 2635
        String telRegex17 = "^\\d{2,3}[)]\\d{2,3}\\d{4}$";

        // 칼텍스 내선번호 2615
        String telRegex18 = "^\\d{4}$";

        // 특이한 국내번호 82-2-6900-4184
        String telRegex19 = "^\\d{1,2}-\\d{1,2}-\\d{3,4}-\\d{3,4}$";

        // 지역번호 없는 국내번호 680 2668
        String telRegex20 = "^\\d{2,3}\\d{3,4}$";

        // 지역번호 없는 국내번호 2 950-2864
        String telRegex21 = "^\\d{2,3}-\\d{3,4}$";

        // 외국 번호 (특이함7) 971-2-674-8987
        String telRegex22 = "^\\d{2,3}-\\d{1,2}-\\d{3,4}-\\d{3,4}$";

        // 칼텍스 내선번호 5-6048
        String telRegex23 = "^[5]-\\d{4}$";

        // 번호 특이하게 입력 042--866-1815
        String telRegex24 = "^\\d{2,3}--\\d{3,4}-\\d{4}$";

        // 1544 로 시작하는 하이픈 없는 번호
        String telRegex25 = "^[1-5]{4}\\d{3,4}$";

        // 번호 특이하게 입력 02--2005--6095
        String telRegex26 = "^\\d{2,3}--\\d{3,4}--\\d{4}$";

        boolean numberMatch =
                Pattern.matches(telRegex1, number) ||
                        Pattern.matches(telRegex2, number) ||
                        Pattern.matches(telRegex3, number) ||
                        Pattern.matches(telRegex4, number) ||
                        Pattern.matches(telRegex5, number) ||
                        Pattern.matches(telRegex6, number) ||
                        Pattern.matches(telRegex7, number) ||
                        Pattern.matches(telRegex8, number) ||
                        Pattern.matches(telRegex9, number) ||
                        Pattern.matches(telRegex10, number) ||
                        Pattern.matches(telRegex11, number) ||
                        Pattern.matches(telRegex12, number) ||
                        Pattern.matches(telRegex13, number) ||
                        Pattern.matches(telRegex14, number) ||
                        Pattern.matches(telRegex15, number) ||
                        Pattern.matches(telRegex16, number) ||
                        Pattern.matches(telRegex17, number) ||
                        Pattern.matches(telRegex18, number) ||
                        Pattern.matches(telRegex19, number) ||
                        Pattern.matches(telRegex20, number) ||
                        Pattern.matches(telRegex21, number) ||
                        Pattern.matches(telRegex22, number) ||
                        Pattern.matches(telRegex23, number) ||
                        Pattern.matches(telRegex24, number) ||
                        Pattern.matches(telRegex25, number) ||
                        Pattern.matches(telRegex26, number);

        return numberMatch;
    }

    public String returnCVNUM(String number, String cmpCd) {
        boolean foreignNum =
                number.substring(0, 2).equals("18") || // 중국 전화번호
                        number.substring(0, 2).equals("13") || // 중국 전화번호
                        number.substring(0, 2).equals("65") ||  // 싱가포르 전화번호
                        number.substring(0, 2).equals("44") || // 영국 전화번호
                        number.substring(0, 3).equals("971") || // 아랍 전화번호
                        number.substring(0, 3).equals("420") || // 체코 전화번호
                        number.substring(0, 2).equals("52") ||  // 멕시코 전화번호
                        number.substring(0, 1).equals("7") || // 러시아 전화번호
                        number.substring(0, 2).equals("91") ||
                        number.substring(0, 2).equals("62") || // 인도네시아
                        (number.substring(0, 1).equals("1") && number.length() > 8) || // 캐나다 전화번호
                        number.substring(0, 2).equals("84") || // 베트남
                        number.substring(0, 2).equals("86") || // 중국
                        number.substring(0, 2).equals("82"); // 한국

        // 그 외 한국번호
        if (!foreignNum) {

            if (number.length() == 4 && cmpCd.equals("C1")) { // 칼텍스 내선번호 체크
                number = "022005" + number;
            } else if (number.length() == 5 && number.charAt(0) == '5' && cmpCd.equals("C1")) { // 칼텍스 내선번호 체크2
                number = "02200" + number;
            } else if (number.substring(0, 3).equals("680")) { // 061 지역번호 미 입력
                number = "061" + number;
            } else if (number.substring(0, 3).equals("950")) { // 062 지역번호 미 입력
                number = "062" + number;
            } else if (number.charAt(0) != '0') { // 그외
                number = "02" + number;
            }

            number = "82" + number.substring(1, number.length());
        }

        return number;
    }

}

