package com.itm.bnote.server.mobileAppstore.model;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.mobileAppstore.AdaptorCDATA;
import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
public @Data
class RequestInfoXmlModel {

    @XmlElement(name = "app_id")
//    @XmlJavaTypeAdapter(AdaptorCDATA.class)
    private String appId;

    @XmlElement(name = "cur_version")
//    @XmlJavaTypeAdapter(AdaptorCDATA.class)
    private String curVersion;

    @XmlElement(name = "emp_id")
//    @XmlJavaTypeAdapter(AdaptorCDATA.class)
    private String empId;

    @XmlElement(name = "platform_type")
    private String platformType;

    @XmlElement(name = "platform_version")
    private String platformVersion;

    private String password;

    @XmlElement(name = "phone_number")
    private String phoneNumber;

    private String imei;

    @XmlElement(name = "mac_address")
    private String macAddress;

    @XmlElement(name = "noti_device_id")
    private String notiDeviceId;

    @XmlElement(name = "app_version")
    @Getter
    private String appVersion;

    @XmlElement(name = "menu_interface_id")
    private String menuInterfaceId = CommonValue.IF_MOBILE_APPSTORE_ID;

    private String ip;

    private String model;

    public void setAppVersion(double appVersion) {
        this.appVersion = String.valueOf(appVersion);
    }
}
