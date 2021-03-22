package com.itm.bnote.server.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_APP_LOG")
public @Data class AppLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX", nullable = false)
    private int idx;

    @Column(name = "APP_PACKAGE_NAME")
    private String appPackageName;

    @Column(name = "ORG_VERSION")
    private double orgVersion;

    @Column(name = "APP_VERSION")
    private double appVersion;

    @Column(name = "DEVICE_OS_TYPE")
    private String deviceOsType;

    @Column(name = "DEVICE_OS_VERSION")
    private String deviceOsVersion;

    @Column(name = "DEVICE_UUID")
    private String deviceUuid;

    @Column(name = "DEVICE_MODEL")
    private String deviceModel;

    @Column(name = "REG_DATE", nullable = false)
    private Date regDate;
}
