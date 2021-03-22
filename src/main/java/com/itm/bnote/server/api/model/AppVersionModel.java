package com.itm.bnote.server.api.model;

import com.itm.bnote.server.common.CommonValue;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

//import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_APP_VERSION")
public @Data class AppVersionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private int appSeq;

    @Column(name = "APP_VERSION", nullable = false)
    private double appVersion;

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
//    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @Column(name = "DEVICE_OS_TYPE", nullable = false)
    private String deviceOsType;

    @Column(name = "UPDATE_MESSAGE")
    private String updateMessage = "Version Update";

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
//    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @Column(name = "APP_FILE_NAME")
    private String appFileName;

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
//    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @Column(name = "FORCE_UPDATE")
    private String forceUpdate;

    @Column(name = "REG_DATE")
    private Date regDate;


    @OneToMany(mappedBy = "appSeq", cascade = CascadeType.ALL)
    @OrderBy("SEQ DESC")
    private List<AppVersionNoticeModel> noticeModel;


    enum versionObject {
        BASIC(0.01),
        INIT(1.0);

        final private double version;

        // enum에서 생성자 같은 역할
        versionObject(double version) {
            this.version = version;
        }

        // 버전 가져오는 함수
        public double getVersion() {
            return version;
        }

        // 버전 업데이트
        public double nextVersion(double currentVersion) {
            return currentVersion + getVersion();
        }
    }

    @Transient
    private int appVersionSize;

    public void setAppVersion(double appVersion) {
        this.appVersion = this.appVersionSize > 0 ? versionObject.valueOf("BASIC").nextVersion(appVersion) : versionObject.valueOf("INIT").getVersion();
    }
}
