package com.itm.bnote.server.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Slf4j
public class MakeCardImgFile {

    private MultipartFile FILE;

    private String PATH;

    public MakeCardImgFile(MultipartFile file, String cardImgSavePath) {
        this.FILE = file;
        this.PATH = cardImgSavePath;
    }

    public String getCardImgFileName() {
        String sourceFileName = this.FILE.getOriginalFilename();
        String destinationFileName = null;
        File destinationFile;

        try {
            String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

            do {
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
                destinationFile = new File(this.PATH + destinationFileName);
            } while (destinationFile.exists());

//            destinationFile.getParentFile().mkdirs();
//            this.FILE.transferTo(destinationFile);

            byte[] data = this.FILE.getBytes();
            FileOutputStream fos = new FileOutputStream(this.PATH + destinationFileName);
            fos.write(data);
            fos.close();

            return destinationFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destinationFileName;
    }
}
