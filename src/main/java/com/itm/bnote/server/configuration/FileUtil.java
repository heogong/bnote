package com.itm.bnote.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component("FileUtil")
public class FileUtil {
	
	private static final int COMPRESSION_LEVEL = 8;

    private static final int BUFFER_SIZE = 1024 * 2;
    
	public String fileWrite(String gsITMExcutivesListJson, String filePathGSITMExcutives, String filePrefixGSITMExcutives) {
		
		String dbFileName = filePrefixGSITMExcutives + currentDateStringMake();
		String dbFilePathName = filePathGSITMExcutives + dbFileName;
		
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(dbFilePathName, true));
			
			fw.write(gsITMExcutivesListJson);
			fw.flush();
			
			fw.close();
			
			log.info("생성된 파일명 : " + dbFilePathName);
			
			// 파일 압축하기
//			zip(dbFilePathName + ".zip");
			
			dbFilePathName = dbFilePathName + ".zip";
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbFileName;
	}
	
	
	/**
     * 지정된 폴더를 Zip 파일로 압축한다.
     * @param sourcePath - 압축 대상 디렉토리
     * @param output - 저장 zip 파일 이름
     * @throws Exception
     */
    public void zip(String sourcePath) throws Exception {

        // 압축 대상(sourcePath)이 디렉토리나 파일이 아니면 리턴한다.
        File sourceFile = new File(sourcePath);
        if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
            throw new Exception("압축 대상의 파일을 찾을 수가 없습니다.");
        }

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream("zip"); // FileOutputStream
            bos = new BufferedOutputStream(fos); // BufferedStream
            zos = new ZipOutputStream(bos); // ZipOutputStream
            zos.setLevel(COMPRESSION_LEVEL); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
            zipEntry(sourceFile, sourcePath, zos); // Zip 파일 생성
            zos.finish(); // ZipOutputStream finish
        } finally {
            if (zos != null) {
                zos.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 압축
     * @param sourceFile
     * @param sourcePath
     * @param zos
     * @throws Exception
     */
    private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos) throws Exception {
        // sourceFile 이 디렉토리인 경우 하위 파일 리스트 가져와 재귀호출
        if (sourceFile.isDirectory()) {
            if (sourceFile.getName().equalsIgnoreCase(".metadata")) { // .metadata 디렉토리 return
                return;
            }
            File[] fileArray = sourceFile.listFiles(); // sourceFile 의 하위 파일 리스트
            for (int i = 0; i < fileArray.length; i++) {
                zipEntry(fileArray[i], sourcePath, zos); // 재귀 호출
            }
        } else { // sourcehFile 이 디렉토리가 아닌 경우
            BufferedInputStream bis = null;
            try {
                String sFilePath = sourceFile.getPath();
                log.info("sFilePath =====> " + sFilePath);
                log.info("sourcePath.length() =====> " + sourcePath.length());
                log.info("sFilePath.length() =====> " + sFilePath.length());
                
                String zipEntryName = sFilePath.substring(sourcePath.length() + 1, sFilePath.length());

                bis = new BufferedInputStream(new FileInputStream(sourceFile));
                ZipEntry zentry = new ZipEntry(zipEntryName);
                zentry.setTime(sourceFile.lastModified());
                zos.putNextEntry(zentry);

                byte[] buffer = new byte[BUFFER_SIZE];
                int cnt = 0;
                while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    zos.write(buffer, 0, cnt);
                }
                zos.closeEntry();
            } finally {
                if (bis != null) {
                    bis.close();
                }
            }
        }
    }
	
	public String currentDateStringMake() {
		
		Date date = new Date();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
		
		return simpleDateFormat.format(date);
	}
	
}
