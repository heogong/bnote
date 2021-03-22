package com.itm.bnote.server.common;

import com.itm.bnote.server.common.exception.PrivateAddressException;
import com.itm.bnote.server.common.model.MakeDataResponseModel;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CallRestApi {
    private String RESULT_API_DATA;
    private String API_URL;
    private MultiValueMap<String, String> PARAM_MAP = new LinkedMultiValueMap<String, String>();
    private List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();

    public CallRestApi(String apiUrl, MultiValueMap<String, String> map) {
        converters.add(new SourceHttpMessageConverter<>());
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());

        API_URL = apiUrl;
        PARAM_MAP = map;
    }

    // REST API 호출
    public void call() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(converters);

        try {
            RESULT_API_DATA = restTemplate.postForObject(API_URL, PARAM_MAP, String.class);
        } catch (HttpClientErrorException ex) {
            throw new PrivateAddressException(ex.getMessage());
        } catch(RestClientException ex) {
            throw new PrivateAddressException(ex.getMessage());
        }
    }

    public JSONObject result() throws Exception {
        try {
            JSONObject feedback = (JSONObject) new JSONParser(JSONParser.MODE_PERMISSIVE).parse(RESULT_API_DATA);
            return feedback;
        } catch (ParseException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
