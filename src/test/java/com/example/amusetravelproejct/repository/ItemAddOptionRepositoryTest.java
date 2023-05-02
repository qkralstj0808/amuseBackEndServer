package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemAddOption;
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
class ItemAddOptionRepositoryTest {

    @Autowired
    private ItemAddOptionRepository itemAddOptionRepository;

    @Autowired
    private IteminfoRepository iteminfoRepository;

    @Test
    public void create() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        ItemAddOption itemAddOption = new ItemAddOption();
        itemAddOption.setOptionName("Option A");
        itemAddOption.setOptionContent("Option A content");
        itemAddOption.setIteminfo(savedIteminfo);

        // when
        ItemAddOption savedItemAddOption = itemAddOptionRepository.save(itemAddOption);

        // then
        assertThat(savedItemAddOption.getId()).isNotNull();
        assertThat(savedItemAddOption.getOptionName()).isEqualTo("Option A");
        assertThat(savedItemAddOption.getOptionContent()).isEqualTo("Option A content");
        assertThat(savedItemAddOption.getIteminfo()).isEqualTo(savedIteminfo);
    }

    @Test
    public void read() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        ItemAddOption itemAddOption = new ItemAddOption();
        itemAddOption.setOptionName("Option A");
        itemAddOption.setOptionContent("Option A content");
        itemAddOption.setIteminfo(savedIteminfo);
        ItemAddOption savedItemAddOption = itemAddOptionRepository.save(itemAddOption);

        // when
        ItemAddOption foundItemAddOption = itemAddOptionRepository.findById(savedItemAddOption.getId()).orElse(null);

        // then
        assertThat(foundItemAddOption).isNotNull();
        assertThat(foundItemAddOption.getId()).isEqualTo(savedItemAddOption.getId());
        assertThat(foundItemAddOption.getOptionName()).isEqualTo(savedItemAddOption.getOptionName());
        assertThat(foundItemAddOption.getOptionContent()).isEqualTo(savedItemAddOption.getOptionContent());
        assertThat(foundItemAddOption.getIteminfo()).isEqualTo(savedItemAddOption.getIteminfo());
    }
    @Test
    public void update() {
        // given
        ItemAddOption itemAddOption = new ItemAddOption();
        itemAddOption.setOptionName("Option 1");
        itemAddOption.setOptionContent("Option Content 1");
        ItemAddOption savedItemAddOption = itemAddOptionRepository.save(itemAddOption);

        // when
        savedItemAddOption.setOptionName("Option 2");
        ItemAddOption updatedItemAddOption = itemAddOptionRepository.save(savedItemAddOption);

        // then
        assertEquals(savedItemAddOption.getId(), updatedItemAddOption.getId());
        assertEquals("Option 2", updatedItemAddOption.getOptionName());
        assertEquals("Option Content 1", updatedItemAddOption.getOptionContent());
    }

    @Test
    public void delete() {
        // given
        ItemAddOption itemAddOption = new ItemAddOption();
        itemAddOption.setOptionName("Option 1");
        itemAddOption.setOptionContent("Option Content 1");
        ItemAddOption savedItemAddOption = itemAddOptionRepository.save(itemAddOption);

        // when
        itemAddOptionRepository.delete(savedItemAddOption);

        // then
        assertFalse(itemAddOptionRepository.findById(savedItemAddOption.getId()).isPresent());
    }
}


