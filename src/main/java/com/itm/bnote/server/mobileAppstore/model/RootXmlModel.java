package com.itm.bnote.server.mobileAppstore.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="gsc_store")
public @Data
class RootXmlModel {
    private CommonXmlModel common;

    @XmlElement(name="request-info")
    private RequestInfoXmlModel requestInfo;
}
