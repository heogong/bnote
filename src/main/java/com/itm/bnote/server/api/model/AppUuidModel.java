package com.itm.bnote.server.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_APP_UUID")
public @Data class AppUuidModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDX", nullable = false)
    private int idx;

    @Column(name = "DEVICE_UUID", nullable = false)
    private String deviceUuid;

    @Column(name = "REG_DATE", nullable = false)
    private Date regDate;
}
