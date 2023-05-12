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

    public ItemImg create(ItemImg itemImg){
        return imgRepository.save(itemImg);
    }

}
