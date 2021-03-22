package com.itm.bnote.server.mobileAppstore.model;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.mobileAppstore.AdaptorCDATA;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public @Data
class CommonXmlModel {
    @XmlElement(name="interface_id")
//    @XmlJavaTypeAdapter(AdaptorCDATA.class)
    private String interfaceId = CommonValue.IF_MOBILE_APPSTORE_ID;
}
