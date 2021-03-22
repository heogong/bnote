package com.itm.bnote.server.coPhone.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_excutivesVersion")
public @Data class CPExcutivesVersionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ", nullable = false)
    private int seq;

    @Column(name = "excutivesMajorVersion", nullable = false)
    private double excutivesMajorVersion;

    @Column(name = "excutivesMinorVersion", nullable = false)
    private double excutivesMinorVersion;

    @Column(name = "deviceOsType", nullable = false)
    private String deviceOsType;

    @Column(name = "appVersion")
    private double appVersion;

    @Column(name = "jsonFilename")
    private String jsonFilename;

    @Column(name = "regDate", nullable = false)
    private Date regDate;

    @Column(name = "updateDate")
    private Date updateDate;
}
