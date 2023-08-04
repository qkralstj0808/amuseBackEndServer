package com.example.amusetravelproejct.config.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Slf4j
public class UtilMethod {
    private  AmazonS3 amazonS3Client;
    static String bucketName = "amuseimg";

    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    public static String[] day = {"sun","mon","tue","wed","thu","fri","sat"};
    public static String[] outGrad = {"Bronze","Silver","Gold","Platinum","Diamond"};

    public static String[] status = {"creat","update","read"};

    public String getImgUrl(String base64Img, String fileName) {
        // TODO
        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
        // 반환
        if (base64Img == "") {
            return null;
        }

        String[] split = base64Img.split(",");

        if(split.length < 2){
            log.info("base64로 인코딩 안된 문자열입니다.");
            log.info(split.toString());
            return base64Img;
        }else{
            log.info("base64 맞다");
        }

        String base64 = base64Img.split(",")[1];
        String type = base64Img.split(";")[0].split(":")[1];
        byte[] imageBytes = Base64Utils.decodeFromString(base64);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(type);
        metadata.setContentLength(imageBytes.length);
        String key = "images/" + fileName;
        amazonS3Client.putObject(bucketName, key, inputStream, metadata);
        String s3Url = amazonS3Client.getUrl(bucketName, key).toString();

        return s3Url;
    }


}
