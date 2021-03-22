package com.itm.bnote.server.mobileAppstore;

import com.itm.bnote.server.common.CommonValue;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Deflater;
import java.util.zip.InflaterInputStream;

@Slf4j
public class NetworkConnection {

    private final String TAG = getClass().getSimpleName();
    private final int TIME_OUT = 30000;

    public String sendHttpRequest(String url, String xmlData) throws Exception {
        String param;
        String resultData = "";
        try {

            SecretKeySpec keySpec = new SecretKeySpec(CommonValue.AES256_KEY_VALUE2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, keySpec);
            byte[] encrypted = cipher.doFinal(xmlData.getBytes("UTF-8"));
            param = new BASE64Encoder().encode(encrypted);

            resultData = outXmlData(url, param);

        } catch (Exception e) {
            throw new Exception(e);
        }

        return resultData;
    }

    public String outXmlData(String callUrl, String param) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        URL url = new URL(callUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("POST");
        urlConnection.setConnectTimeout(TIME_OUT);
        urlConnection.setRequestProperty("Content-Type", "application/xml");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        OutputStream outputStream = urlConnection.getOutputStream();

        byte[] dataByte = param.getBytes();

        // 데이터 압축
        Deflater def = new Deflater();
        def.setLevel(-1);
        def.setInput(dataByte);
        def.finish();

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream(dataByte.length);

        byte[] buf = new byte[1024];

        while (!def.finished()) {
            int compByte = def.deflate(buf);
            byteArray.write(buf, 0, compByte);
        }

        byte[] comData = byteArray.toByteArray();

        try {
            byteArray.close();
        } catch (IOException ioe) {
            throw new IOException(ioe);
        }
        outputStream.write(comData);
        outputStream.close();

        // 리턴 데이터
        InputStream ins = urlConnection.getInputStream();

        // 데이터 압축 해제
        InflaterInputStream iis = new InflaterInputStream(ins);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 데이터 -> string
        int c;
        while ((c = iis.read()) != -1) {
            baos.write(c);
        }
        byte[] byteBodyValue = baos.toByteArray();

        // base64 디코드 - 기존 base64 코드가 존재해 클래스화 하지 않음
        BASE64Decoder decode = new BASE64Decoder();
        byte[] bytrDecBase64Value = decode.decodeBuffer(new String(byteBodyValue, "UTF-8"));

        SecretKeySpec keySpec = new SecretKeySpec(CommonValue.AES256_KEY_VALUE2.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, keySpec);
        byte[] decrtpyed = cipher.doFinal(bytrDecBase64Value);

        String strBodyValue = new String(decrtpyed, "UTF-8");

        log.info(strBodyValue);

        return strBodyValue;
    }
}
