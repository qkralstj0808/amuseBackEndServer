package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemImg;
import com.example.amusetravelproejct.repository.ImgRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class ItemImgService {

    private final ImgRepository imgRepository;

    public String saveItemImg(String img, Item item) {
        // TODO
        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
        // 반환

        ItemImg itemImg = new ItemImg();
        itemImg.setImgUrl(img);
        itemImg.setItem(item);
        imgRepository.save(itemImg);
        return img;
    }

    public List<String> saveItemImgs(List<String> imgs, Item item) {
        // TODO
        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
        // 반환

        imgs.forEach(img -> {
            ItemImg itemImg = new ItemImg();
            itemImg.setImgUrl(img);
            itemImg.setItem(item);
            imgRepository.save(itemImg);
        });
        return imgs;
    }
}
