package com.example.amusetravelproejct.config.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Slf4j
@Service
public class UtilMethod {
    private  final AmazonS3 amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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
        log.info("base64 : " + base64);
        String type = base64Img.split(";")[0].split(":")[1];
        log.info("type : " + type);
        byte[] imageBytes = Base64Utils.decodeFromString(base64);
        log.info("imageBytes : " + imageBytes.length);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        log.info("inputStream : " + inputStream.toString());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(type);
        metadata.setContentLength(imageBytes.length);

        if(fileName == null || fileName.equals("")){
            fileName = String.valueOf(System.currentTimeMillis() / 1000);
            log.info("fileName : " + fileName);
        }
        String key = "images/" + fileName;

        log.info("bucket : " + bucket);
        amazonS3Client.putObject(bucket, key, inputStream, metadata);
        String s3Url = amazonS3Client.getUrl(bucket, key).toString();

        return s3Url;
    }


}
