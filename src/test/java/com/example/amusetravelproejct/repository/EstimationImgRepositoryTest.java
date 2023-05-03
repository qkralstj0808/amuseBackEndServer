package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.EstimationImg;
import com.example.amusetravelproejct.domain.ItemEstimation;
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
class EstimationImgRepositoryTest {

    @Autowired
    private EstimationImgRepository estimationImgRepository;

    @Test
    public void create() {
        // given
        EstimationImg estimationImg = new EstimationImg();
        estimationImg.setImgUrl("https://example.com/image.jpg");
        ItemEstimation itemEstimation = new ItemEstimation();
        // Set the necessary fields for itemEstimation here
        estimationImg.setItemEstimation(itemEstimation);

        // when
        EstimationImg savedEstimationImg = estimationImgRepository.save(estimationImg);

        // then
        assertNotNull(savedEstimationImg.getId());
        assertEquals("https://example.com/image.jpg", savedEstimationImg.getImgUrl());
        assertEquals(itemEstimation, savedEstimationImg.getItemEstimation());
    }

    @Test
    public void read() {
        // given
        EstimationImg estimationImg = new EstimationImg();
        estimationImg.setImgUrl("https://example.com/image.jpg");
        ItemEstimation itemEstimation = new ItemEstimation();
        // Set the necessary fields for itemEstimation here
        estimationImg.setItemEstimation(itemEstimation);
        EstimationImg savedEstimationImg = estimationImgRepository.save(estimationImg);

        // when
        Optional<EstimationImg> foundEstimationImg = estimationImgRepository.findById(savedEstimationImg.getId());

        // then
        assertTrue(foundEstimationImg.isPresent());
        assertEquals(savedEstimationImg, foundEstimationImg.get());
    }

    @Test
    public void update() {
        // given
        EstimationImg estimationImg = new EstimationImg();
        estimationImg.setImgUrl("https://example.com/image.jpg");
        ItemEstimation itemEstimation = new ItemEstimation();
        // Set the necessary fields for itemEstimation here
        estimationImg.setItemEstimation(itemEstimation);
        EstimationImg savedEstimationImg = estimationImgRepository.save(estimationImg);

        // when
        savedEstimationImg.setImgUrl("https://example.com/new-image.jpg");
        EstimationImg updatedEstimationImg = estimationImgRepository.save(savedEstimationImg);

        // then
        assertEquals(savedEstimationImg.getId(), updatedEstimationImg.getId());
        assertEquals("https://example.com/new-image.jpg", updatedEstimationImg.getImgUrl());
    }

    @Test
    public void delete() {
        // given
        EstimationImg estimationImg = new EstimationImg();
        estimationImg.setImgUrl("https://example.com/image.jpg");
        ItemEstimation itemEstimation = new ItemEstimation();
        // Set the necessary fields for itemEstimation here
        estimationImg.setItemEstimation(itemEstimation);
        EstimationImg savedEstimationImg = estimationImgRepository.save(estimationImg);

        // when
        estimationImgRepository.delete(savedEstimationImg);

        // then
        assertFalse(estimationImgRepository.existsById(savedEstimationImg.getId()));
    }
}