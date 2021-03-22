package com.itm.bnote.server.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_APP_VERSION_NOTICE")
public @Data class AppVersionNoticeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private int seq;

    @Column(name = "APP_SEQ", nullable = false)
    private int appSeq;

    @Column(name = "NOTICE_MESSAGE", nullable = false)
    private String noticeMessage;

    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    @Column(name = "FORCE_UPDATE", nullable = false)
    private String forceUpdate;

    @Column(name = "REG_DATE")
    private Date regDate;

}
