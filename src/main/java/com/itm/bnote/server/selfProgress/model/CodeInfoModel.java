package com.itm.bnote.server.selfProgress.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TB_CODE_INFO")
public @Data
class CodeInfoModel {
    @Id
    @Column(name = "SEQ", nullable = false)
    private int seq;

    @Column(name = "CODE_GRP", nullable = false)
    private String codeGrp;

    @Column(name = "CODE_NO", nullable = false)
    private String codeNo;

    @Column(name = "CODE_NM", nullable = false)
    private String codeNm;

    @Column(name = "CODE_VALUE", nullable = false)
    private String codeValue;

    @Column(name = "REG_DATE", nullable = false)
    private Date regDate;
}
