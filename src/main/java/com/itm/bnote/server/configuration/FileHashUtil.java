package com.itm.bnote.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileHashUtil {

    private String FILE_NAME;

    @Value("${file.path.orgListSavePath}")
    String FILE_SAVE_PATH;


    public String setFileName(String fileName) {
        this.FILE_NAME = fileName;

        return getFileHash();
    }

    public String getFileHash() {
        String SHA = "";
        int buff = 16384;

        String filename = FILE_SAVE_PATH + FILE_NAME;

        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            MessageDigest hashSum = MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[buff];
            byte[] partialHash = null;

            long read = 0;

            // calculate the hash of the hole file for the test
            long offset = file.length();
            int unitsize;
            while (read < offset) {
                unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
                file.read(buffer, 0, unitsize);

                hashSum.update(buffer, 0, unitsize);

                read += unitsize;
            }

            file.close();
            partialHash = new byte[hashSum.getDigestLength()];
            partialHash = hashSum.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < partialHash.length; i++) {
                sb.append(Integer.toString((partialHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SHA;
    }
}
