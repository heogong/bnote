package com.itm.bnote.server;

import com.itm.bnote.server.common.CommonValue;
import com.itm.bnote.server.common.exception.PrivateAddressException;
import com.itm.bnote.server.mobileAppstore.NetworkConnection;
import com.itm.bnote.server.configuration.AES256Util;
import com.itm.bnote.server.mobileAppstore.model.CommonXmlModel;
import com.itm.bnote.server.mobileAppstore.model.RootXmlModel;
import com.itm.bnote.server.mobileAppstore.model.RequestInfoXmlModel;
import com.itm.bnote.server.organization.model.OrgEmpInfoModel;
import com.itm.bnote.server.organization.repository.OrgEmpInfoRepository;
import com.itm.bnote.server.selfProgress.model.CodeInfoModel;
import com.itm.bnote.server.selfProgress.repository.CodeInfoRepository;
import com.lgcns.ikep.common.security.SecurityEncrypter;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTests {

    @Autowired
    private OrgEmpInfoRepository orgEmpInfoRepository;


    @Test
    public void contextLoads() {

        String[] phoneList = {
                "010-0000-0000",
                "010-000-0000",
                "02-2005-5555",
                "02-000-0000",
                "041-123-0000",
                "+45 045621543",
                "(02)2005-5454",
                "(031)2005-5454",
                "546521454532",
                "01021213215",
                "0220054564",
                "821000000000",
                "1577-1577",
                "02-1577-1577",
                "061)680-3516",
                "(62)811-880-0324",
                "950-2864",
                "18630420213",
                "63019399",
                "65-9177-0015",
                "971-50-127-8286",
                "44-78-8885-4041",
                "1032170774",
                "+65-9011-7702",
                "1-678-739-7383",
                "52-181-2861-6926",
                "420-605-430-091",
                "52-181-2350-4155",
                "7-925-924-9917",
                "1-678-739-7383",
                "010 5627 7802",
                "010- 8148- 2207",
                "65 9869 1362",
                "010-23254991",
                "010.2325.1562",
                "420-725-776-414",
                "971-5657-7363",
                "(62)811-880-0324",
                "010) 2582-1257",
                "010~2582~1257",
                "420 725 710 000",
                "91-99302-99680",
                "8490 240 59440",
                "84 93 789 1244",
                "86 156-1364-7111",
                "86-185 5112 3177",
                "(86) 186 6226 9101",
                "420-725-386687",
                "971-5657-73654",
                "86176-8545-5019",
                "86-176-8583 2266",
                "91-99302-99680",
                "061)6802635",
                "061)680 2646",
                "82-2-6900-4184",
                "2645-6",
                "6050/6025",
                "5013(5014)",
                "971-2-674-8987",
                "(061)680-2614~5",
                "061)680-2614~5",
                "061-680-2623-4",
                "2675~7",
                "5-6048",
                "042--866-1815",
                "3948 (단축:4707)",
                "15441151",
                "061-680-2725,6",
                "061-680-2725~28",
                "02-2005-1968~75",
                "6236/6080",
                "680-5053,4,5",
                "061-680-5053--6",
                "02--2005--6095",
                "02-2005-6058,9",
                "86-316-6086740~8004",
                "2675~7",
                "+91-99302-99680",
                "+7-925-924-9917",
                "+1-678-739-7383",
                "+420-595-390705",
                "+8210-6481-0127",
                "+86-512-63019399"
        };


        // 전화번호 체크
        String telRegex1 = "^\\d{2,3}-\\d{3,4}-\\d{4}$"; // 첫 번째 체크

        // 하이픈 없는 번호 체크
        String telRegex2 = "^\\d{2,3}\\d{3,4}\\d{4}$"; // 두 번째 체크

        // 괄호 지역번호 체크
        String telRegex3 = "^.(\\d{2,3}.)\\d{3,4}-\\d{4}$"; // 세 번째 체크

        // 4글자 번호
        String telRegex4 = "^\\d{4}-\\d{4}$"; // 네 번째 체크

        // 외국 번호
        String telRegex5 = "^\\d{1,3}-\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // 하이픈 없는 외국번호
        String telRegex6 = "^\\d{1,3}\\d{2,3}\\d{3,4}\\d{4}$";

        // . 구분
        String telRegex7 = "^\\d{2,3}.\\d{3,4}.\\d{4}$";

        // - 하이픈 하나
        String telRegex8 = "^\\d{2,3}-\\d{3,4}\\d{4}$";

        // 외국 번호 괄호
        String telRegex9 = "^.(\\d{2,3}.)\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // ~ 구분
        String telRegex10 = "^\\d{2,3}~\\d{3,4}~\\d{4}$";

        // 외국 번호 (특이함)
        String telRegex11 = "^\\d{1,2}-\\d{2,5}-\\d{3,5}$";

        // 외국 번호 (특이함2) 86 156-1364-7111
        String telRegex12 = "^\\d{1,2}\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // 외국 번호 (특이함3) (86) 186 6226 9101
        String telRegex13 = "^.(\\d{1,2}.)\\d{2,3}\\d{3,4}\\d{3,4}$";

        // 외국 번호 (특이함4) 86-185 5112 3177
        String telRegex14 = "^\\d{1,2}-\\d{2,3}\\d{3,4}\\d{3,4}$";

        // 외국 번호 (특이함5) 420-725-386687
        String telRegex15 = "^\\d{2,3}-\\d{3,4}-\\d{3,7}$";

        // 외국 번호 (특이함6) 86-176-8583 2266
        String telRegex16 = "^\\d{1,2}-\\d{2,3}-\\d{3,4}\\d{3,4}$";

        // 국내번호 반 괄호 061)680 2635
        String telRegex17 = "^\\d{2,3}[)]\\d{2,3}\\d{4}$";

        // 내선번호 2615
        String telRegex18 = "^\\d{4}$";

        // 특이한 국내번호 82-2-6900-4184
        String telRegex19 = "^\\d{1,2}-\\d{1,2}-\\d{3,4}-\\d{3,4}$";

        // 지역번호 없는 국내번호 680 2668
        String telRegex20 = "^\\d{2,3}\\d{3,4}$";

        // 지역번호 없는 국내번호 2 950-2864
        String telRegex21 = "^\\d{2,3}-\\d{3,4}$";

        // 외국 번호 (특이함7) 971-2-674-8987
        String telRegex22 = "^\\d{2,3}-\\d{1,2}-\\d{3,4}-\\d{3,4}$";

        // 칼텍스 내선번호 5-6048
        String telRegex23 = "^[5]-\\d{4}$";

        // 번호 특이하게 입력 042--866-1815
        String telRegex24 = "^\\d{2,3}--\\d{3,4}-\\d{4}$";

        // 1544 로 시작하는 하이픈 없는 번호
        String telRegex25 = "^[1-5]{4}\\d{3,4}$";

        // 번호 특이하게 입력 02--2005--6095
        String telRegex26 = "^\\d{2,3}--\\d{3,4}--\\d{4}$";

        // 외국 번호 +91-99302-99680
        String telRegex27 = "^[+]\\d{1,2}-\\d{4,5}-\\d{4,5}$";

        // 외국 번호 +7-925-924-9917
        String telRegex28 = "^[+]\\d{1,2}-\\d{3,4}-\\d{3,4}-\\d{3,4}$";

        // 외국 번호 +420-595-390705
        String telRegex29 = "^[+]\\d{3,4}-\\d{3,4}-\\d{5,6}$";

        // 국제 번호 한국 +8210-6481-0127
        String telRegex30 = "^[+]\\d{3,4}-\\d{3,4}-\\d{3,4}$";

        // 외국 번호 +86-512-63019399
        String telRegex31 = "^[+]\\d{1,2}-\\d{3,4}-\\d{7,8}$";

        // 사내번호 다중1 2645-6
        String telDupRegex1 = "^\\d{4}-\\d$";

        // 사내번호 다중2 6050/6025
        String telDupRegex2 = "^\\d{4}/\\d{4}$";

        // 사내번호 다중3 5013(5014), 3948(단축:4707)
        String telDupRegex3 = "^\\d{4}[(][가-힣.:.0-9]*[)]$";

        //  다중4 (061)680-2614~5
        String telDupRegex4 = "^.(\\d{2,3}.)\\d{3,4}-\\d{3,4}~\\d{1,2}$";

        //  다중5 061-680-2614-5
        String telDupRegex5 = "^\\d{2,3}-\\d{3,4}-\\d{3,4}-\\d{1,2}$";

        //  다중6 2675~5
        String telDupRegex6 = "^\\d{4}~\\d{1,2}$";

        //  다중7 061-680-2725,6
        String telDupRegex7 = "^\\d{2,3}-\\d{3,4}-\\d{3,4},\\d{1,2}$";

        //  다중8 02-2005-1968~75
        String telDupRegex8 = "^\\d{2,3}-\\d{3,4}-\\d{3,4}~\\d{1,2}$";

        //  다중9 680-5053,4,5
        String telDupRegex9 = "^\\d{2,3}-\\d{3,4},\\d{1,2},\\d{1,2}$";

        //  다중10 061-680-5053--6
        String telDupRegex10 = "^\\d{2,3}-\\d{3,4}-\\d{3,4}--\\d{1,2}$";

        //  다중11 86-316-6086740~8004
        String telDupRegex11 = "^\\d{2,3}-\\d{3,4}-\\d{5,7}~\\d{3,4}$";

        // 사내번호 다중12 2725~7
        String telDupRegex12 = "^\\d{4}~\\d$";


//        // 하이픈 없는 폰번호
//        String phoneRegex2 = "^01(?:0|1[6-9])(?:\\d{3}|\\d{4})\\d{4}$"; // 두 번째 체크

        for (String phone : phoneList) {
            phone = phone.replaceAll("\\p{Z}", "");

            boolean matches =
                    Pattern.matches(telRegex1, phone) ||
                            Pattern.matches(telRegex2, phone) ||
                            Pattern.matches(telRegex3, phone) ||
                            Pattern.matches(telRegex4, phone) ||
                            Pattern.matches(telRegex5, phone) ||
                            Pattern.matches(telRegex6, phone) ||
                            Pattern.matches(telRegex7, phone) ||
                            Pattern.matches(telRegex8, phone) ||
                            Pattern.matches(telRegex9, phone) ||
                            Pattern.matches(telRegex10, phone) ||
                            Pattern.matches(telRegex11, phone) ||
                            Pattern.matches(telRegex12, phone) ||
                            Pattern.matches(telRegex13, phone) ||
                            Pattern.matches(telRegex14, phone) ||
                            Pattern.matches(telRegex15, phone) ||
                            Pattern.matches(telRegex16, phone) ||
                            Pattern.matches(telRegex17, phone) ||
                            Pattern.matches(telRegex18, phone) ||
                            Pattern.matches(telRegex19, phone) ||
                            Pattern.matches(telRegex20, phone) ||
                            Pattern.matches(telRegex21, phone) ||
                            Pattern.matches(telRegex22, phone) ||
                            Pattern.matches(telRegex23, phone) ||
                            Pattern.matches(telRegex24, phone) ||
                            Pattern.matches(telRegex25, phone) ||
                            Pattern.matches(telRegex26, phone) ||
                            Pattern.matches(telRegex27, phone) ||
                            Pattern.matches(telRegex28, phone) ||
                            Pattern.matches(telRegex29, phone) ||
                            Pattern.matches(telRegex30, phone) ||
                            Pattern.matches(telRegex31, phone);

            String chPhone;

            if (matches) {
                String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
                chPhone = phone.replaceAll(match, "");

                // 중국 전화번호
                if (chPhone.substring(0, 2).equals("18") || chPhone.substring(0, 2).equals("13")) {
                    System.out.println("중국번호 : " + chPhone);

                }

                // 싱가포르 전화번호
                if (chPhone.substring(0, 2).equals("65")) {
                    System.out.println("싱가포르 : " + chPhone);
                }

                // 영국 전화번호
                if (chPhone.substring(0, 2).equals("44")) {
                    System.out.println("영국 : " + chPhone);

                }

                // 아랍 전화번호
                if (chPhone.substring(0, 3).equals("971")) {
                    System.out.println("아랍 : " + chPhone);

                }

                // 체코 전화번호
                if (chPhone.substring(0, 3).equals("420")) {
                    System.out.println("체코 : " + chPhone);

                }

                // 멕시코 전화번호
                if (chPhone.substring(0, 2).equals("52")) {
                    System.out.println("멕시코 : " + chPhone);

                }

                // 러시아 전화번호
                if (chPhone.substring(0, 1).equals("7")) {
                    System.out.println("러시아 : " + chPhone);

                }

                // 인도 전화번호
                if (chPhone.substring(0, 2).equals("91")) {
                    System.out.println("인도 : " + chPhone);

                }

                // 인도네시아 전화번호
                if (chPhone.substring(0, 2).equals("62")) {
                    System.out.println("인도 : " + chPhone);

                }

                // 캐나다 전화번호
                if (chPhone.substring(0, 1).equals("1") && chPhone.length() > 8) {
                    System.out.println("캐나다 : " + chPhone);

                }

                // 베트남 전화번호
                if (chPhone.substring(0, 2).equals("84")) {
                    System.out.println("베트남 : " + chPhone);
                }

                // 베트남 전화번호
                if (chPhone.substring(0, 2).equals("86")) {
                    System.out.println("중국 : " + chPhone);
                }

                // 한국 국제번호
                if (chPhone.substring(0, 2).equals("82")) {
                    System.out.println("한국 : " + chPhone);
                }

                if (chPhone.length() == 4) {
                    chPhone = "022005" + chPhone;
                } else if (chPhone.length() == 5 && chPhone.charAt(0) == '5') {
                    chPhone = "02200" + chPhone;
                } else if (chPhone.substring(0, 3).equals("680")) {
                    chPhone = "061" + chPhone;

                } else if (chPhone.substring(0, 3).equals("950")) {
                    chPhone = "062" + chPhone;

                } else if (chPhone.charAt(0) != '0') {
                    chPhone = "02" + chPhone;
                }

                System.out.println(phone + " => " + chPhone + " => " + "82" + chPhone.substring(1, chPhone.length()));

            } else {
                if (Pattern.matches(telDupRegex1, phone) || Pattern.matches(telDupRegex5, phone)) {
                    String phoenCV[] = phone.split("-");

                    if (phoenCV.length > 2) {
                        System.out.println("- 으로 다중일 경우 입력 : " + phone + " => " + phoenCV[0] + phoenCV[1] + phoenCV[2]);
                    } else {
                        System.out.println("- 으로 중복 입력 : " + phone + " => " + phoenCV[0]);
                    }

                } else if (Pattern.matches(telDupRegex2, phone)) {
                    String phoenCV[] = phone.split("/");
                    System.out.println("/ 으로 중복 입력 : " + phone + " => " + phoenCV[0]);
                } else if (Pattern.matches(telDupRegex3, phone)) {
                    String phoenCV[] = phone.split("\\(");
                    System.out.println("() 으로 중복 입력 : " + phone + " => " + phoenCV[0]);

                } else if (Pattern.matches(telDupRegex4, phone) || Pattern.matches(telDupRegex6, phone) || Pattern.matches(telDupRegex8, phone) || Pattern.matches(telDupRegex11, phone) || Pattern.matches(telDupRegex12, phone)) {
                    String phoenCV[] = phone.split("~");
                    System.out.println("~ 으로 중복 입력 : " + phone + " => " + phoenCV[0]);

                } else if (Pattern.matches(telDupRegex7, phone) || Pattern.matches(telDupRegex9, phone)) {
                    String phoenCV[] = phone.split(",");
                    System.out.println(", 으로 중복 입력 : " + phone + " => " + phoenCV[0]);

                } else if (Pattern.matches(telDupRegex10, phone)) {
                    String phoenCV[] = phone.split("--");
                    System.out.println(", 으로 중복 입력 : " + phone + " => " + phoenCV[0]);

                } else {
                    System.out.println("비정상 폰 번호 : " + phone);
                }

            }

//            System.out.println(matches + " : " + phone);
        }

    }


    @Test
    public void decryAes256() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://mobile.gscaltex.co.kr/MobileAppstore/fileData/Bnote/file/IT_Organization_190711172956";
        String resultStr2 = restTemplate.getForObject(url, String.class);

//        String local = "D:\\\\CO_PHONE_RESOURCE\\\\file\\\\GSITM_Excutives_190703130524";


        String iv;
        Key keySpec;

        String key = CommonValue.AES256_KEY_VALUE;
        try {

            iv = key.substring(0, 16);
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec2 = new SecretKeySpec(keyBytes, "AES");

            keySpec = keySpec2;

            Cipher c = null;

            c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] byteStr = Base64.decodeBase64(resultStr2.getBytes());


            String resultStr = new String(c.doFinal(byteStr), "UTF-8");

            System.out.println(resultStr);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void securityTest() throws Exception {
        String key = "p6z3OQIwgpGz0GodttjyCA==";
        String iv = "TOFFICE";
//        String iv = "IKEP";

        Date date = new Date();
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

        SecurityEncrypter security = new SecurityEncrypter(key, iv);
        String data = "ID=c1u3490,EMAIL=c1u3490@gscaltex.com,DAY=2019-07-01";
        security.encrypt(data);

        String encodeResult = URLEncoder.encode(security.encrypt(data), "UTF-8");

        String enc = security.encrypt(data);

        System.out.println(encodeResult);

//        System.out.println(security.encrypt(data));
//        System.out.println(security.decrypt(enc));
    }

    @Test
    public void zipExample() {
        String path = "D:\\TEST_FILE";

        byte[] buf = new byte[1024];

        File getFile = new File(path);

        File[] fileList = getFile.listFiles();

        ZipOutputStream outputStream = null;
        FileInputStream fileInputStream = null;

        try {
            outputStream = new ZipOutputStream(new FileOutputStream("D:\\TEST_FILE\\result.zip"));

            for (File file : fileList) {
//                System.out.println("fileName : " + file.getName());

                fileInputStream = new FileInputStream(file);
                outputStream.putNextEntry(new ZipEntry(String.valueOf(file)));

                int length = 0;
                while (((length = fileInputStream.read()) > 0)) {
                    outputStream.write(buf, 0, length);
                }

                outputStream.closeEntry();
                fileInputStream.close();
            }
            outputStream.close();
        } catch (IOException e) {
            // Exception Handling
        } finally {
            try {
                outputStream.closeEntry();
                outputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                // Exception Handling
            }
        }
    }

    @Test
    public void createZipFile() {

//        String path = "https://gscsm.ddns.net:9919/TEST_FILE/sample.jpg"; // 압축 파일 및 폴더
        String path = "D:\\TEST_FILE"; // 압축 대상 파일 및 폴더
        String toPath = "D:\\TEST_FILE\\"; // 압축 경로
        String fileName = "resultZIP.zip";

        File dir = new File(path);
        String[] list = dir.list();
        String _path;

        if (!dir.canRead() || !dir.canWrite())
            return;

        int len = list.length;

        System.out.println(len);
        System.out.println(len);
        System.out.println(len);
        System.out.println(len);


        if (path.charAt(path.length() - 1) != '/')
            _path = path + "/";
        else
            _path = path;

        try {
            ZipOutputStream zip_out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(toPath + "/" + fileName), 2048));

            for (int i = 0; i < len; i++)
                zip_folder("", new File(_path + list[i]), zip_out);

            zip_out.close();

        } catch (FileNotFoundException e) {
            //

        } catch (IOException e) {
            //
        } finally {

        }
    }


    private void zip_folder(String parent, File file, ZipOutputStream zout) throws IOException {
        String ZIP_FROM_PATH = "https://gscsm.ddns.net:9919/TEST_FILE\\"; // 압축 경로

        byte[] data = new byte[2048];
        int read;

        System.out.println("isFile : " + file.isFile());
        System.out.println("isDirectory : " + file.isDirectory());

        if (file.isFile()) {
            ZipEntry entry = new ZipEntry(parent + file.getName());
            zout.putNextEntry(entry);
            BufferedInputStream instream = new BufferedInputStream(new FileInputStream(file));

            while ((read = instream.read(data, 0, 2048)) != -1)
                zout.write(data, 0, read);

            zout.flush();
            zout.closeEntry();
            instream.close();

        } else if (file.isDirectory()) {
            String parentString = file.getPath().replace(ZIP_FROM_PATH, "");
            parentString = parentString.substring(0, parentString.length() - file.getName().length());
            ZipEntry entry = new ZipEntry(parentString + file.getName() + "/");
            zout.putNextEntry(entry);

            String[] list = file.list();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; i++) {
                    zip_folder(entry.getName(), new File(file.getPath() + "/" + list[i]), zout);
                }
            }
        }
    }

    @Test
    public void springLargeDown() {

        String[] fileName = {"sample", "sample2", "sample3"};

        for (final String file : fileName) {
            RestTemplate restTemplate = new RestTemplate();

            // Optional Accept header
            RequestCallback requestCallback = new RequestCallback() {
                @Override
                public void doWithRequest(ClientHttpRequest clientHttpRequest) throws IOException {
                    clientHttpRequest.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
                }
            };

            ResponseExtractor<Void> responseExtractor = new ResponseExtractor() {
                @Override
                public Object extractData(ClientHttpResponse clientHttpResponse) throws IOException {

                    Path path = Paths.get("D:\\TEST_FILE2\\" + "/" + file + ".jpg");
                    Files.copy(clientHttpResponse.getBody(), path);
                    return null;
                }
            };


            restTemplate.execute(URI.create("https://gscsm.ddns.net:9919/TEST_FILE/" + file + ".jpg"), HttpMethod.GET, requestCallback, responseExtractor);

        }
    }


    @Test
    public void fileHash() throws Exception {
        String SHA = "";
        int buff = 16384;

        String filename = "D:\\\\CO_PHONE_RESOURCE\\\\file\\\\GSITM_Excutives_190702112617";
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
        }

        System.out.println("fileHash file : " + SHA);

        //4bbb3ff96a1d6ccf5e76397d024ef984950d6e17d2c9cbcbe0e79e540531a881
        //4bbb3ff96a1d6ccf5e76397d024ef984950d6e17d2c9cbcbe0e79e540531a881
        //82df8f1ce9cd517ddd0a3c24aa2de6cff83f2cc69c51e6817cc1e803e8d048b0
        //82df8f1ce9cd517ddd0a3c24aa2de6cff83f2cc69c51e6817cc1e803e8d048b0
    }


    @Test
    public void ldapAuth() throws Exception {
        DirContext ctx = null;

        Hashtable<String, String> property = new Hashtable<String, String>();
        property.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        property.put(Context.PROVIDER_URL, "ldap://192.168.32.53:389");
        property.put(Context.SECURITY_AUTHENTICATION, "simple");
        property.put(Context.SECURITY_PRINCIPAL, "ldapadmin@devgsitm.com");    // 최초 인증용 계정
        property.put(Context.SECURITY_CREDENTIALS, "ldapadmin123!@#");      // 최초 인증용 계정비밀번호

        String userid = "it0998";
        String userPwd = "1111"; // 신규 서버는 주민번호 뒷자리

        ctx = new InitialDirContext(property);

        SearchControls cons = new SearchControls();
        cons.setSearchScope(SearchControls.SUBTREE_SCOPE);

//            String filter = "(sAMAccountName="+login.getEmpNo()+")"; // sAMAccountName 을 기준으로 검색.
        String filter = "(sAMAccountName=" + userid + ")"; // sAMAccountName 을 기준으로 검색.

        //NamingEnumeration<SearchResult> result = ctx.search("CN=Users,DC=GSITM,DC=COM", filter, cons);

        //그룹웨어 테스트
        NamingEnumeration<SearchResult> result = ctx.search("OU=사용자,OU=GSITM,OU=TopGroup,DC=devgsitm,DC=com", filter, cons);


        //그룹웨어 운영
        //NamingEnumeration<SearchResult> result = ctx.search("OU=사용자,OU=GSITM,OU=TopGroup,DC=gwgsitm,DC=com", filter, cons);

        int matchCount = 0;
        while (result.hasMore()) {
            SearchResult entry = result.next();

            // sAMAccountName 이 일치하는 유저가 발견되었다면 해당 유저로 로그인 시도
            property.put(Context.SECURITY_PRINCIPAL, entry.getNameInNamespace());
            property.put(Context.SECURITY_CREDENTIALS, userPwd);

            // 패스워드가 틀리다면 여기서 AuthenticationException 발생.
            ctx = new InitialDirContext(property);

            ++matchCount;
        }
    }

    @Test
    public void orgNumberProcessing() {

        String[] phoneList = {
                "061)680-2721",
                "010)8160-1515",
                "3567",
                "680-5023",
                "01071711059",
                "98785432456",
                "680-2713",
                "+86-18551123177",
                "+5218115160296",
                "010~7288~4516",
                "061)680~2533",
                "+52-181-2350-4155",
                "010 9204 2191",
                "5-6140",
                "(061)680-2705",
                "680-2543",
                "+8210-2121-2350",
                "+822-0000-0101",
                "+821021212350",
                "+82200000101",
                "02-2005-5954",
                "010-2012-7645",
                "0220055923",
                "0616802533"
        };

        String specialCharPattern = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]"; // 모든 특수문자

        // 국내번호 반 괄호 061)680-2721
        String telRegex1 = "^\\d{2,3}[)]\\d{3,4}-\\d{4}$";

        // 내선번호 2615
        String telRegex2 = "^\\d{4}$";

        // 칼텍스 내선번호 5-6048
        String telRegex3 = "^[5]-\\d{4}$";

        // 010~7288~4516
        String telRegex4 = "^\\d{2,3}~\\d{3,4}~\\d{4}$";

        // 01071711059
        String telRegex5 = "^[0-1]{3}\\d{3,4}\\d{3,4}$";

        // 680-2713
        String telRegex6 = "^\\d{3,4}-\\d{4}$";

        // 061)680~2533
        String telRegex7 = "^\\d{2,3}[)]\\d{3,4}~\\d{4}$";

        // 010 9204 2191
        String telRegex8 = "^\\d{2,3} \\d{3,4} \\d{4}$";

        // 국제 번호1 +86-18551123177
        String telRegex9 = "^[+]\\d{2}-\\d{10,11}$";

        // 국제 번호2 +5218115160296
        String telRegex10 = "^[+]\\d{13}$";

        // 국제 번호 3 +52-181-2350-4155
        String telRegex11 = "^[+]\\d{2}-\\d{2,3}-\\d{3,4}-\\d{3,4}$";

        // 국제 번호 4 (한국) +8210-2121-2350 / +822-0000-0101
        String telRegex12 = "^[+]\\d{3,4}-\\d{3,4}-\\d{3,4}$";

        // 국제 번호 5 (한국) +821021212350
        String telRegex13 = "^[+]82\\d{10}$";

        // 국제 번호 6 (한국)  +82200000101
        String telRegex14 = "^[+]82\\d{9}$";

        // (061)680-2705
        String telRegex15 = "^[(]\\d{2,3}[)]\\d{3,4}-\\d{4}$";

        // 0220055923
        String telRegex16 = "^[0-2]{2}\\d{3,4}\\d{3,4}$";

        // 0616802533
        String telRegex17 = "^[0-6]{3}\\d{3,4}\\d{3,4}$";


        String replacePhone;

        for (String phone : phoneList) {
            if (Pattern.matches(telRegex1, phone) || Pattern.matches(telRegex4, phone) || Pattern.matches(telRegex7, phone)) {
                replacePhone = phone.replaceAll(specialCharPattern, "-");

                System.out.println("1 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex2, phone)) { // C1일 경우 조건 추가
                replacePhone = "02-2005-" + phone;

                System.out.println("2 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex3, phone)) { // C1일 경우 조건 추가
                replacePhone = "02-200" + phone;

                System.out.println("3 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex5, phone)) {
                replacePhone = phone.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");

                System.out.println("5 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex6, phone)) {
                if (phone.substring(0, 3).equals("680")) {
                    replacePhone = "061-" + phone;
                    System.out.println("4 : " + phone + " => " + replacePhone);
                }
            } else if (Pattern.matches(telRegex8, phone)) {
                replacePhone = phone.replaceAll(" ", "-");
                System.out.println("5 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex9, phone) || Pattern.matches(telRegex10, phone) || Pattern.matches(telRegex11, phone)) {
                replacePhone = phone.replaceAll(specialCharPattern, "");
                System.out.println("6 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex12, phone)) {
                phone = phone.substring(1, phone.length());
                replacePhone = phone.replaceAll(phone.substring(0, 2), "0");
                System.out.println("7 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex13, phone)) {
                replacePhone = phone.replaceAll("[+]82", "0").replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
                System.out.println("8 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex14, phone)) {
                replacePhone = phone.replaceAll("[+]82", "0").replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-$2-$3");
                System.out.println("9 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex15, phone)) {
                replacePhone = phone.replaceAll(specialCharPattern, "").replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
                System.out.println("10 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex16, phone)) {
                replacePhone = phone.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-$2-$3");
                System.out.println("11 : " + phone + " => " + replacePhone);
            } else if (Pattern.matches(telRegex17, phone)) {
                replacePhone = phone.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
                System.out.println("12 : " + phone + " => " + replacePhone);
            } else {
                System.out.println("NO 체크번호 : " + phone);
            }

        }
    }

    @Test
    public void basicPattern() {

        String[] phoneList = {
                "061)680-2721",
                "010)8160-1515",
                "3567",
                "680-5023",
                "01071711059",
                "98785432456",
                "680-2713",
                "+86-18551123177",
                "+5218115160296",
                "010~7288~4516",
                "061)680~2533",
                "+52-181-2350-4155",
                "010 9204 2191",
                "5-6140",
                "(061)680-2705",
                "680-2543",
                "+8210-2121-2350",
                "+822-0000-0101",
                "+821021212350",
                "+82200000101",
                "02-2005-5954",
                "010-2012-7645",
                "0220055923",
                "0616802533"
        };

        OrgEmpInfoModel orgEmpInfoModel = new OrgEmpInfoModel();

        for (String phone : phoneList) {
            System.out.println(orgEmpInfoModel.getChkBasicPattern(phone.replaceAll("\\p{Z}", ""), "C1"));
        }

    }


    @Test
    public void cron() {
        String cronValue = "0 0 12 * * ?";

        Pattern lpExecCtcle1 = Pattern.compile("0 ([0-5]{0,1}[0-9]{1} ([0-2]{0,1}[0-9]{1})\\*\\*\\?$ )");

        Matcher m = lpExecCtcle1.matcher(cronValue);
    }


    @Test
    public void XmlUtil() {
        JAXBContext context = null;
        List<RootXmlModel> list = null;
        CommonXmlModel commonXmlModel = new CommonXmlModel();
        RequestInfoXmlModel requestInfoXmlModel = new RequestInfoXmlModel();
        Marshaller marshaller = null;

        RootXmlModel rootXmlModel = new RootXmlModel();

        try {
            context = JAXBContext.newInstance(RootXmlModel.class);

            commonXmlModel.setInterfaceId("IF-0016");
            rootXmlModel.setCommon(commonXmlModel);

            requestInfoXmlModel.setAppId("com.gsitm.mobile.b_note");
            requestInfoXmlModel.setCurVersion("1.11");
            requestInfoXmlModel.setEmpId("C1S1004");
            requestInfoXmlModel.setPlatformType("ios");
            requestInfoXmlModel.setPlatformVersion("9");
            requestInfoXmlModel.setPassword("<![CDATA[asdf12]]>");
            requestInfoXmlModel.setPhoneNumber("<![CDATA[0103332222]]>");
            requestInfoXmlModel.setImei("<![CDATA[357735040124968]]>");
            requestInfoXmlModel.setMacAddress("<![CDATA[]]>");
            requestInfoXmlModel.setNotiDeviceId("<![CDATA[0]]>");
            requestInfoXmlModel.setAppVersion(1.07);
            requestInfoXmlModel.setMenuInterfaceId("<![CDATA[test_test]]>");
            requestInfoXmlModel.setIp("<![CDATA[10.200.201.200]]>");
            requestInfoXmlModel.setModel("<![CDATA[model]]>");
            rootXmlModel.setRequestInfo(requestInfoXmlModel);


            // Marshal 객체를 XML로 변환
            // Unmarshal the objects from XML
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);


            StringWriter sw = new StringWriter();
            marshaller.marshal(rootXmlModel, sw);

            String result = sw.toString();

            NetworkConnection networkConnection = new NetworkConnection();


        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void encTest() throws Exception {

        String str = "abc";
        String key = CommonValue.AES256_KEY_VALUE2;
        String enStr = "";


        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] textBytes = str.getBytes("UTF-8");

        SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"),
                "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, newKey);


        System.out.println(Base64.encodeBase64(cipher.doFinal(textBytes)));

    }


    @Test
    public void excetpionTest() {
        throw new PrivateAddressException("주소록 등록 에러발생");
    }


    enum IFerrorCode {

        NO_BODY("0001"),
        FORM_ERROR("0002"),
        VAL_NONE("0003"),
        VAL_ERROR("0004"),
        REQ_DIFF("1001"),
        NO_AUTH_USR("1002"),
        NO_VER("1003"),
        NO_OS_VER("1004"),
        NO_DEVICE_ID("1005"),
        ERR_CON_DB("2001"),
        ERR_QUERY_DB("2002"),
        NO_SEL_RESULT("2003"),
        NO_INST_RESULT("2004"),
        NO_DEL_RESULT("2005"),
        NO_FILE_RESULT("3001"),
        ERR_FILE_PRO("3002"),
        ERR_PWD_PRO("4001"),
        ERR_PWD_DIFF("4002"),
        ERR_AUTH("4003"),
        ERR_PWD("9993"),
        NO_REG_USR("9994"),
        ERR_COMM("9999");

        final private String errorCode;

        // enum에서 생성자 같은 역할
        IFerrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorCode() {
            return errorCode;
        }
    }

    @Test
    public void returnTest() {
        System.out.println(IFerrorCode.valueOf("NO_BODY").getErrorCode());

        System.out.println(IFerrorCode.ERR_AUTH.getErrorCode());

        System.out.println(IFerrorCode.values().equals("NO_BODY"));

//        for(IFerrorCode aa : IFerrorCode.values()) {
//
//            System.out.println(aa.errorCode);
//
//        }


    }
}


