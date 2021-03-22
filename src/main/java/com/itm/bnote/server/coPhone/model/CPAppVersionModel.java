package com.itm.bnote.server.coPhone.model;

import com.itm.bnote.server.common.CommonValue;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

//import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "tb_appVersion")
public @Data class CPAppVersionModel {

    @Id
    @Column(name = "idx", nullable = false)
    private int idx;

    @Column(name = "appVersion", nullable = false)
    private double appVersion;

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
//    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @Column(name = "deviceOsType", nullable = false)
    private String deviceOsType;

    @Column(name = "updatemessage")
    private String updateMessage = "Version Update";

    @NotNull(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
//    @NotEmpty(message = CommonValue.ERROR_CHECK_COMMONREQUEST_MESSAGE)
    @Column(name = "appFileName")
    private String appFileName;

    @Column(name = "regDate")
    private Date regDate;
}
