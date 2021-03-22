package com.itm.bnote.server.common;

import com.itm.bnote.server.configuration.AES256Util;
import com.itm.bnote.server.configuration.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
public class MakeEncryFile {

    // 생성 파일 경로
    private String FILE_PATH;

    // 생성할 파일 prefix
    private String FILE_PREFIX;

    private String GENERATED_FILE;

    private String JSON_DATA;


    public MakeEncryFile(String jsonData) {
        this.JSON_DATA = jsonData;
    }

    public void setDefaultInfo(String filePrefix, String filePath) {
        this.FILE_PREFIX = filePrefix;
        this.FILE_PATH = filePath;
    }

    public void makeData() throws Exception {
        String gsITMExcutivesListJsonAES256;    // JSON으로 변환된 임직원 정보 aes256 암호화 데이터
        AES256Util aes256Util = new AES256Util();
        FileUtil fileUtil = new FileUtil();

        try {
            log.info("===== 임직원 암호화 START =====");
            gsITMExcutivesListJsonAES256 = aes256Util.encrypt(this.JSON_DATA);

            log.info("===== 임직원 암호화 END =====");

            log.info("===== 임직원 파일 생성 Start =====");
            GENERATED_FILE = fileUtil.fileWrite(gsITMExcutivesListJsonAES256, FILE_PATH, FILE_PREFIX);
//            GENERATED_FILE = fileUtil.fileWrite(this.JSON_DATA, FILE_PATH, FILE_PREFIX);
            log.info("===== 임직원 파일 생성 END =====");

        } catch (IOException ex) {
            throw new IOException();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String getResultFileName() {
        return this.GENERATED_FILE;
    }
}
