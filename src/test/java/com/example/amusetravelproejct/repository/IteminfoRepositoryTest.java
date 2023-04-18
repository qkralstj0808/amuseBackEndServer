package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Iteminfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class IteminfoRepositoryTest {


    @Autowired
    private IteminfoRepository iteminfoRepository;

    @Test
    public void create() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);

        // when
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        // then
        assertThat(savedIteminfo.getId()).isNotNull();
        assertThat(savedIteminfo.getItemCode()).isEqualTo("ITEM001");
        assertThat(savedIteminfo.getItemTitle()).isEqualTo("Item 1");
        assertThat(savedIteminfo.getStartingPrice()).isEqualTo(1000L);
    }

    @Test
    public void update() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        // when
        savedIteminfo.setItemTitle("Item 1 - updated");
        Iteminfo updatedIteminfo = iteminfoRepository.save(savedIteminfo);

        // then
        assertThat(updatedIteminfo.getId()).isEqualTo(savedIteminfo.getId());
        assertThat(updatedIteminfo.getItemCode()).isEqualTo(savedIteminfo.getItemCode());
        assertThat(updatedIteminfo.getItemTitle()).isEqualTo("Item 1 - updated");
        assertThat(updatedIteminfo.getStartingPrice()).isEqualTo(savedIteminfo.getStartingPrice());
    }

    @Test
    public void delete() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        // when
        iteminfoRepository.delete(savedIteminfo);

        // then
        assertThat(iteminfoRepository.findById(savedIteminfo.getId())).isEmpty();
    }

    @Test
    public void read() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        // when
        Optional<Iteminfo> foundIteminfo = iteminfoRepository.findById(savedIteminfo.getId());

        // then
        assertThat(foundIteminfo).isNotEmpty();
        assertThat(foundIteminfo.get().getId()).isEqualTo(savedIteminfo.getId());
        assertThat(foundIteminfo.get().getItemCode()).isEqualTo(savedIteminfo.getItemCode());
        assertThat(foundIteminfo.get().getItemTitle()).isEqualTo(savedIteminfo.getItemTitle());
        assertThat(foundIteminfo.get().getStartingPrice()).isEqualTo(savedIteminfo.getStartingPrice());
    }

    @Test
    public void read_all() {

        iteminfoRepository.deleteAll();

        // given
        Iteminfo iteminfo1 = new Iteminfo();
        iteminfo1.setItemCode("ITEM001");
        iteminfo1.setItemTitle("Item 1");
        iteminfo1.setStartingPrice(1000L);
        Iteminfo savedIteminfo1 = iteminfoRepository.save(iteminfo1);

        Iteminfo iteminfo2 = new Iteminfo();
        iteminfo2.setItemCode("ITEM002");
        iteminfo2.setItemTitle("Item 2");
        iteminfo2.setStartingPrice(2000L);
        Iteminfo savedIteminfo2 = iteminfoRepository.save(iteminfo2);

        Iteminfo iteminfo3 = new Iteminfo();
        iteminfo3.setItemCode("ITEM003");
        iteminfo3.setItemTitle("Item 3");
        iteminfo3.setStartingPrice(3000L);
        Iteminfo savedIteminfo3 = iteminfoRepository.save(iteminfo3);

        // when

        List<Iteminfo> iteminfos = iteminfoRepository.findAll();

        // then
        assertThat(iteminfos.size()).isEqualTo(3);
        assertThat(iteminfos.get(0).getId()).isEqualTo(savedIteminfo1.getId());
        assertThat(iteminfos.get(0).getItemCode()).isEqualTo("ITEM001");
        assertThat(iteminfos.get(0).getItemTitle()).isEqualTo("Item 1");
        assertThat(iteminfos.get(0).getStartingPrice()).isEqualTo(1000L);
        assertThat(iteminfos.get(1).getId()).isEqualTo(savedIteminfo2.getId());
        assertThat(iteminfos.get(1).getItemCode()).isEqualTo("ITEM002");
        assertThat(iteminfos.get(1).getItemTitle()).isEqualTo("Item 2");
        assertThat(iteminfos.get(1).getStartingPrice()).isEqualTo(2000L);
        assertThat(iteminfos.get(2).getId()).isNotNull();
        assertThat(iteminfos.get(2).getItemCode()).isEqualTo("ITEM003");
        assertThat(iteminfos.get(2).getItemTitle()).isEqualTo("Item 3");
        assertThat(iteminfos.get(2).getStartingPrice()).isEqualTo(3000L);
    }
}