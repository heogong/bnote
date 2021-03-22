package com.itm.bnote.server.configuration;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

//import org.apache.tomcat.util.codec.binary.Base64;

@Component("RSAUtil")
public class RSAUtil {

//    private static Logger logger = Logger.getLogger(RSAUtil.class);

    /**
     * Public Key로 암호화한 후 결과로 출력된 byte 배열을 Base64로 인코딩하여 String으로 변환하여 리턴함
     *
     * @param text      암호화할 텍스트
     * @param 'publicKey' RSA 공개키
     * @return Base64로 인코딩된 암호화 문자열
     * @throws UnsupportedEncodingException
     */
    public String encrypt(String text, String strPublicKey) throws Exception {
        String encryptedText = null;

        try {
            PublicKey publicKey = stringToPublicKey(strPublicKey);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));

//            Base64.Encoder encoder = Base64.getEncoder();
//            encryptedText = new String(encoder.encode(encrypted));
            encryptedText = new String(Base64.encodeBase64(encrypted));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        }
        return encryptedText;
    }

    /**
     * decode 시킨 후 RSA 비밀키(Private Key)를 이용하여 암호화된 텍스트를 원문으로 복호화
     */
    public String decrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        String decryptedText = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

//            Base64.Decoder decoder = Base64.getDecoder();
//            byte[] byteStr = decoder.decode(encryptedText.getBytes());
            byte[] byteStr = Base64.decodeBase64(encryptedText.getBytes());
            decryptedText = new String(cipher.doFinal(byteStr), "UTF-8");

        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
            throw new Exception(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new Exception(e.getMessage());
        }
        return decryptedText;
    }


    // 단말로부터 받은 Public Key값(String -> PublicKey) 형변환
    public PublicKey stringToPublicKey(String strPublicKey) throws Exception {
//        logger.info("strPublicKey ==========> " + strPublicKey);
        PublicKey publicKey = null;
        try {

            strPublicKey = strPublicKey.replaceAll("\n", "");

//            Base64.Decoder decoder = Base64.getDecoder();
//            byte[] keyBytes = decoder.decode(strPublicKey);
            byte[] keyBytes = Base64.decodeBase64(strPublicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

            KeyFactory kf;
            kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);

        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new Exception(e.getMessage());
        }

        return publicKey;
    }

    // KeyPair 생성
    public KeyPair generateKeyPair() throws Exception {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator clsKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
            clsKeyPairGenerator.initialize(2048);
            keyPair = clsKeyPairGenerator.genKeyPair();

        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e.getMessage());
        }
        return keyPair;
    }

    /**
     * keyPair 값을 이용하여 PublicKey 객체를 생성함
     */
    public PublicKey getPublicKey(KeyPair keyPair) throws Exception {
        PublicKey publicKey = null;
        try {
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return publicKey;
    }

    /**
     * keyPair 값을 이용하여 PrivateKey 객체를 생성함
     */
    public PrivateKey getPrivateKey(KeyPair keyPair) throws Exception {
        PrivateKey privateKey = null;
        try {
            privateKey = keyPair.getPrivate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return privateKey;
    }
}