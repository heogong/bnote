package com.itm.bnote.server.mobileAppstore;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdaptorCDATA extends XmlAdapter<String, String> {

    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }

    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }
}

