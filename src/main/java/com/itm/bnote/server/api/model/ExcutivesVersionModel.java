package com.itm.bnote.server.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_EXCUTIVES_VERSION")
public class ExcutivesVersionModel {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false)
    private int seq;

    @Getter
    @Setter
    @Column(name = "DEVICE_OS_TYPE", nullable = false)
    private String deviceOsType;

    @Getter
    @Setter
    @Column(name = "COMP")
    private String comp;

    @Getter
    @Column(name = "EXCUTIVES_MAJOR_VERSION", nullable = false)
    private double excutivesMajorVersion;

    @Getter
    @Column(name = "EXCUTIVES_MINOR_VERSION", nullable = false)
    private double excutivesMinorVersion;

    @Getter
    @Column(name = "JSON_FILE_NAME")
    private String jsonFilename;

    @Getter
    @Column(name = "EXT_JSON_FILE_NAME")
    private String extJsonFilename;

    @Getter
    @Setter
    @Column(name = "FORCE_UPDATE", nullable = false)
    private String forceUpdate;

    @Getter
    @Setter
    @Column(name = "REG_DATE", nullable = false)
    private Date regDate;

    @Getter
    @Setter
    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Getter
    @Setter
    @Column(name = "FILE_HASH_DATA")
    private String fileHashData;


    @Getter
    @Setter
    @Transient
    private int excutivesVersionSize;

    @Getter
    @Setter
    @Transient
    private Boolean extOrgCompany;

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

    public void setExcutivesMajorVersion(double excutivesMajorVersion) {
        this.excutivesMajorVersion = this.excutivesVersionSize > 0 ? excutivesMajorVersion : versionObject.valueOf("INIT").getVersion();
    }

    public void setExcutivesMinorVersion(double excutivesMinorVersion) {
        this.excutivesMinorVersion = this.excutivesVersionSize > 0 ? versionObject.valueOf("BASIC").nextVersion(excutivesMinorVersion) : versionObject.valueOf("INIT").getVersion();
    }

    public void setJsonFilename(String jsonFilename, String oldJsonFileName) {
        this.jsonFilename = this.extOrgCompany ? oldJsonFileName : jsonFilename;
    }

    public void setExtJsonFilename(String extJsonFilename, String oldExtJsonFileName) {
        this.extJsonFilename = this.extOrgCompany ? extJsonFilename : oldExtJsonFileName;
    }
}
