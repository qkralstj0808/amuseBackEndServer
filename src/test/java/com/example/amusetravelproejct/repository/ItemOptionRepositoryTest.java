package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemOption;
<<<<<<< HEAD
=======
>>>>>>> develop
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
<<<<<<< HEAD
=======
>>>>>>> develop

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ItemOptionRepositoryTest {

    @Autowired
    private ItemOptionRepository itemOptionRepository;

    @Test
    public void create() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemOption itemOption = new ItemOption();
        itemOption.setEmogi("👍");
        itemOption.setContent("good");
        itemOption.setIteminfo(iteminfo);

        ItemOption savedItemOption = itemOptionRepository.save(itemOption);

        assertThat(savedItemOption.getId()).isNotNull();
        assertThat(savedItemOption.getEmogi()).isEqualTo("👍");
    }

    @Test
    public void read() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemOption itemOption = new ItemOption();
        itemOption.setEmogi("👍");
        itemOption.setContent("good");
        itemOption.setIteminfo(iteminfo);

        ItemOption savedItemOption = itemOptionRepository.save(itemOption);
        Long id = savedItemOption.getId();

        Optional<ItemOption> foundItemOption = itemOptionRepository.findById(id);

        assertThat(foundItemOption.isPresent()).isTrue();
        assertThat(foundItemOption.get().getId()).isEqualTo(id);
        assertThat(foundItemOption.get().getEmogi()).isEqualTo("👍");
    }

    @Test
    public void update() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemOption itemOption = new ItemOption();
        itemOption.setEmogi("👍");
        itemOption.setContent("good");
        itemOption.setIteminfo(iteminfo);

        ItemOption savedItemOption = itemOptionRepository.save(itemOption);
        Long id = savedItemOption.getId();

        ItemOption foundItemOption = itemOptionRepository.findById(id).get();
        foundItemOption.setContent("excellent");
        itemOptionRepository.save(foundItemOption);

        ItemOption updatedItemOption = itemOptionRepository.findById(id).get();
        assertThat(updatedItemOption.getId()).isEqualTo(id);
        assertThat(updatedItemOption.getContent()).isEqualTo("excellent");
    }

    @Test
    public void delete() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemOption itemOption = new ItemOption();
        itemOption.setEmogi("👍");
        itemOption.setContent("good");
        itemOption.setIteminfo(iteminfo);

        ItemOption savedItemOption = itemOptionRepository.save(itemOption);
        Long id = savedItemOption.getId();

        itemOptionRepository.deleteById(id);

        Optional<ItemOption> foundItemOption = itemOptionRepository.findById(id);
        assertThat(foundItemOption.isPresent()).isFalse();
    }

}