package com.itm.bnote.server.mobileAppstore;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.mobileAppstore.model.CommonXmlModel;
import com.itm.bnote.server.mobileAppstore.model.RequestInfoXmlModel;
import com.itm.bnote.server.mobileAppstore.model.RootXmlModel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class ObjToXml {

    private CommonRequestModel REQUEST_DATA;
    private Marshaller marshaller = null;
    RootXmlModel ROOT_XML_MODEL = new RootXmlModel();

    public ObjToXml(CommonRequestModel commonRequestModel) {

        this.REQUEST_DATA = commonRequestModel;

    }

    public void makeXml() throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(RootXmlModel.class);
        CommonXmlModel commonXmlModel = new CommonXmlModel();
        RequestInfoXmlModel requestInfoXmlModel = new RequestInfoXmlModel();

        // <request-info>
        requestInfoXmlModel.setAppId(this.REQUEST_DATA.getAppPackageName());
        requestInfoXmlModel.setCurVersion(this.REQUEST_DATA.getCurVersion());
        requestInfoXmlModel.setEmpId(this.REQUEST_DATA.getEmpId());
        requestInfoXmlModel.setPlatformType(this.REQUEST_DATA.getDeviceOsType());
        requestInfoXmlModel.setPlatformVersion(this.REQUEST_DATA.getDeviceOsVersion());
        requestInfoXmlModel.setPassword(this.REQUEST_DATA.getPassword());
        requestInfoXmlModel.setPhoneNumber(this.REQUEST_DATA.getPhoneNumber());
        requestInfoXmlModel.setImei(this.REQUEST_DATA.getImei());
        requestInfoXmlModel.setMacAddress(this.REQUEST_DATA.getMacAddress());
        requestInfoXmlModel.setNotiDeviceId(this.REQUEST_DATA.getNotiDeviceId());
        requestInfoXmlModel.setAppVersion(this.REQUEST_DATA.getAppVersion());
        requestInfoXmlModel.setIp(this.REQUEST_DATA.getIp());
        requestInfoXmlModel.setModel(this.REQUEST_DATA.getDeviceModel());

        ROOT_XML_MODEL.setCommon(commonXmlModel);
        ROOT_XML_MODEL.setRequestInfo(requestInfoXmlModel);

        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    }

    public String resultXmlDate() throws JAXBException {
        // xml 결과
        StringWriter sw = new StringWriter();
        marshaller.marshal(ROOT_XML_MODEL, sw);

        return sw.toString();
    }
}
