package com.example.amusetravelproejct.controller.admin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
@AllArgsConstructor
public class UtilMethod {
    private AmazonS3 amazonS3Client;
    static String bucketName = "amuse-img";

    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    public String getCourseImgUrl(String base64Img, String fileName) {
        // TODO
        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
        // 반환

        byte[] imageBytes = Base64.getDecoder().decode(base64Img);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpeg");
        metadata.setContentLength(imageBytes.length);
        String key = "images/" + fileName;
        amazonS3Client.putObject(bucketName, key, inputStream, metadata);
        String s3Url = amazonS3Client.getUrl(bucketName, key).toString();

        return s3Url;
    }


}
