package com.itm.bnote.server.mobileAppstore.service.Impl;

import com.itm.bnote.server.api.model.CommonRequestModel;
import com.itm.bnote.server.api.model.CommonResponseModel;
import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.mobileAppstore.NetworkConnection;
import com.itm.bnote.server.mobileAppstore.ObjToXml;
import com.itm.bnote.server.mobileAppstore.service.MobileAppstoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MobileAppstoreServiceImpl implements MobileAppstoreService {

    @Value("${api.mobileAppStore}")
    String apiMobileAppstore;

    @Override
    public CommonResponseModel APIcheckAppVersion(CommonRequestModel commonRequestModel, CommonResponseModel commonResponseModel) throws JAXBException, ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        // request 데이터 to xml
        ObjToXml objToXml = new ObjToXml(commonRequestModel);
        objToXml.makeXml();
        String xmlResult = objToXml.resultXmlDate();
        // request 데이터 to xml

        NetworkConnection networkConnection = new NetworkConnection();
        String responseXml = null;
        try {
            responseXml = networkConnection.sendHttpRequest(apiMobileAppstore, xmlResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // xml to 객체화
        InputSource is = new InputSource(new StringReader(responseXml));
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);

        // xpath parse
        XPath xpath = XPathFactory.newInstance().newXPath();

        // NodeList 가져오기 : <common>, <response_info> : app 업데이트 있을 경우메만 존재
        NodeList common = (NodeList) xpath.evaluate("//common/*", document, XPathConstants.NODESET);
        NodeList responseInfo = (NodeList) xpath.evaluate("//response_info/*", document, XPathConstants.NODESET);

        // xml 데이터 맵 set
        Map<String, String> xmlMap = new HashMap<>();

        for (int i = 0; i < common.getLength(); i++) {
            xmlMap.put(common.item(i).getNodeName(), common.item(i).getTextContent());
        }

        // app 업데이트 버전 없을 경우 요청된 버전값 set
        commonResponseModel.setAppVersion(commonRequestModel.getAppVersion());

        // app 업데이트 존재여부
        if(responseInfo.getLength() > 0) {
            for (int i = 0; i < responseInfo.getLength(); i++) {
                xmlMap.put(responseInfo.item(i).getNodeName(), responseInfo.item(i).getTextContent());
            }

            commonResponseModel.setReturnCode(xmlMap.get("force_update_yn").equals("Y") ? CommonValue.APP_FORCE_UPDATE_CODE : CommonValue.APP_NORMAL_UPDATE_CODE);
            commonResponseModel.setReturnMessage(xmlMap.get("update_comment"));
            commonResponseModel.setAppUpdateYn("Y");
            commonResponseModel.setAppForceUpdate(xmlMap.get("force_update_yn"));
            commonResponseModel.setAppVersion(Double.parseDouble(xmlMap.get("new_app_version")));
        }

        // 인증오류 코드
        List<String> IFerorCodeList = new ArrayList();
        IFerorCodeList.add("0001");
        IFerorCodeList.add("0002");
        IFerorCodeList.add("0003");
        IFerorCodeList.add("0004");
        IFerorCodeList.add("1001");
        IFerorCodeList.add("1002");
        IFerorCodeList.add("1003");
        IFerorCodeList.add("1004");
        IFerorCodeList.add("1005");
        IFerorCodeList.add("2001");
        IFerorCodeList.add("2002");
        IFerorCodeList.add("2003");
        IFerorCodeList.add("2004");
        IFerorCodeList.add("2005");
        IFerorCodeList.add("3001");
        IFerorCodeList.add("3002");
        IFerorCodeList.add("4001");
        IFerorCodeList.add("4002");
        IFerorCodeList.add("4003");
        IFerorCodeList.add("9993");
        IFerorCodeList.add("9994");
        IFerorCodeList.add("9999");

        // 인증오류 여부 체크
        for(String errorCode : IFerorCodeList) {
            if(errorCode.equals(xmlMap.get("auth_return_code"))) {
                commonResponseModel.setReturnCode(xmlMap.get("auth_return_code"));
                commonResponseModel.setReturnMessage(xmlMap.get("auth_return_msg"));
            }
        }

        commonResponseModel.setIsNew(xmlMap.get("is_new"));
        commonResponseModel.setIsPwChange(xmlMap.get("is_pw_change"));
        commonResponseModel.setPwChangePrompt(xmlMap.get("pw_change_prompts"));
        commonResponseModel.setIsPwPolicy(xmlMap.get("is_pw_policy"));
        commonResponseModel.setFileSize(xmlMap.get("file_size"));
        commonResponseModel.setNewId(xmlMap.get("new_id"));

        return commonResponseModel;
    }
}
