package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Img;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ImgRepositoryTest {

    @Autowired
    private ImgRepository imgRepository;

    @Test
    public void create() {
        Img img = new Img();
        img.setImgUrl("http://example.com/image.jpg");

        Img savedImg = imgRepository.save(img);
        assertNotNull(savedImg.getId());
    }

    @Test
    public void read() {
        Img img = new Img();
        img.setImgUrl("http://example.com/image.jpg");

        Img savedImg = imgRepository.save(img);
        Long imgId = savedImg.getId();

        Optional<Img> foundImg = imgRepository.findById(imgId);
        assertTrue(foundImg.isPresent());
        assertEquals(savedImg, foundImg.get());
    }

    @Test
    public void update() {
        Img img = new Img();
        img.setImgUrl("http://example.com/image.jpg");

        Img savedImg = imgRepository.save(img);
        Long imgId = savedImg.getId();

        Img updatedImg = new Img();
        updatedImg.setId(imgId);
        updatedImg.setImgUrl("http://example.com/new-image.jpg");

        Img savedUpdatedImg = imgRepository.save(updatedImg);
        assertEquals(savedUpdatedImg.getId(), imgId);
        assertEquals(savedUpdatedImg.getImgUrl(), "http://example.com/new-image.jpg");
    }

    @Test
    public void delete() {
        Img img = new Img();
        img.setImgUrl("http://example.com/image.jpg");

        Img savedImg = imgRepository.save(img);
        Long imgId = savedImg.getId();

        imgRepository.delete(savedImg);

        Optional<Img> foundImg = imgRepository.findById(imgId);
        assertFalse(foundImg.isPresent());
    }
}