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
import java.util.Base64;
import java.util.HashMap;
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

        Pattern pattern = Pattern.compile("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
        Matcher matcher = pattern.matcher(base64Img);

        // base64 문자열이 아닌 경우
        if(matcher.find()){
            return base64Img;
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
