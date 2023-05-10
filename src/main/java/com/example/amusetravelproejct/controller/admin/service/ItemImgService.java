package com.example.amusetravelproejct.controller.admin.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemImg;
import com.example.amusetravelproejct.repository.ImgRepository;
import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@Service
public class ItemImgService {

    private final ImgRepository imgRepository;

    private final AmazonS3 amazonS3Client;

    static String bucketName = "amuse-img";



    public String saveItemImg(String base64Img, Item item) {
        // TODO
        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
        // 반환

        byte[] imageBytes = Base64.getDecoder().decode(base64Img);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/jpge");
        metadata.setContentLength(imageBytes.length);


        String key = "images/" + base64Img.hashCode();
        amazonS3Client.putObject(bucketName, key, inputStream, metadata);

        ItemImg itemImg = new ItemImg();
        String s3Url = amazonS3Client.getUrl(bucketName, key).toString();
        itemImg.setImgUrl(s3Url);
        itemImg.setItem(item);
        imgRepository.save(itemImg);
        return s3Url;
    }

    public List<String> saveItemImgs(List<String> base64Imgs,List<String> keys ,Item item) {
        // TODO
        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
        // 반환
        List<String> urls = new ArrayList<>();

        for (int i = 0; i < base64Imgs.size(); i++){
            ItemImg itemImg = new ItemImg();
            byte[] imageBytes = Base64.getDecoder().decode(base64Imgs.get(i));
            InputStream inputStream = new ByteArrayInputStream(imageBytes);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.setContentLength(imageBytes.length);

            String key = "images/" + keys.get(i);
            amazonS3Client.putObject(bucketName, key, inputStream, metadata);
            String s3Url = amazonS3Client.getUrl(bucketName, key).toString();

            itemImg.setImgUrl(s3Url);
            urls.add(s3Url);
            itemImg.setItem(item);

            imgRepository.save(itemImg);
        }

        return urls;
    }

    public void store(String fullPath, String data, String fileName) {
        byte[] imgData = Base64.getDecoder().decode(data);
        File file = new File(fileName);

    }

    public static File getImageFromBase64(String base64String, String fileName) {

        byte[] data = DatatypeConverter.parseBase64Binary(base64String);
        File file = new File(fileName + LocalDateTime.now());
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
