package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemCourse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional


class ItemCourseRepositoryTest {
    @Autowired
    private ItemCourseRepository itemCourseRepository;

    @Test
    public void create() {
        // given
        ItemCourse itemCourse = new ItemCourse();
        itemCourse.setItemCourseTitle("Title");
        itemCourse.setItemCourseContent("Content");
        itemCourse.setItemCourseSequenceId(1L);
        itemCourse.setItemImageUrl("image-url");
        itemCourse.setLatitude(37.12345);
        itemCourse.setLongitude(-122.12345);

        // when
        ItemCourse savedItemCourse = itemCourseRepository.save(itemCourse);

        // then
        assertThat(savedItemCourse.getId()).isNotNull();
        assertThat(savedItemCourse.getItemCourseTitle()).isEqualTo("Title");
        assertThat(savedItemCourse.getItemCourseContent()).isEqualTo("Content");
        assertThat(savedItemCourse.getItemCourseSequenceId()).isEqualTo(1L);
        assertThat(savedItemCourse.getItemImageUrl()).isEqualTo("image-url");
        assertThat(savedItemCourse.getLatitude()).isEqualTo(37.12345);
        assertThat(savedItemCourse.getLongitude()).isEqualTo(-122.12345);
    }

    @Test
    public void read() {
        // given
        ItemCourse itemCourse = new ItemCourse();
        itemCourse.setItemCourseTitle("Title");
        ItemCourse savedItemCourse = itemCourseRepository.save(itemCourse);

        // when
        ItemCourse foundItemCourse = itemCourseRepository.findById(savedItemCourse.getId()).orElse(null);

        // then
        assertThat(foundItemCourse).isNotNull();
        assertThat(foundItemCourse.getId()).isEqualTo(savedItemCourse.getId());
        assertThat(foundItemCourse.getItemCourseTitle()).isEqualTo("Title");
    }

    @Test
    public void update() {
        // given
        ItemCourse itemCourse = new ItemCourse();
        itemCourse.setItemCourseTitle("Title");
        ItemCourse savedItemCourse = itemCourseRepository.save(itemCourse);

        // when
        savedItemCourse.setItemCourseTitle("Updated Title");
        ItemCourse updatedItemCourse = itemCourseRepository.save(savedItemCourse);

        // then
        assertThat(updatedItemCourse).isNotNull();
        assertThat(updatedItemCourse.getId()).isEqualTo(savedItemCourse.getId());
        assertThat(updatedItemCourse.getItemCourseTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void delete() {
        // given
        ItemCourse itemCourse = new ItemCourse();
        itemCourse.setItemCourseTitle("Title");
        ItemCourse savedItemCourse = itemCourseRepository.save(itemCourse);

        // when
        itemCourseRepository.deleteById(savedItemCourse.getId());

        // then
        ItemCourse deletedItemCourse = itemCourseRepository.findById(savedItemCourse.getId()).orElse(null);
        assertThat(deletedItemCourse).isNull();
    }
}