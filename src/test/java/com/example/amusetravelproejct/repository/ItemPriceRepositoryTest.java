package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemPrice;
<<<<<<< HEAD
=======
>>>>>>> develop
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
<<<<<<< HEAD
=======
>>>>>>> develop

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ItemPriceRepositoryTest {

    @Autowired
    private ItemPriceRepository itemPriceRepository;

    @Test
    public void create() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle ("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(1500L);
        itemPrice.setIteminfo(iteminfo);

        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);

        assertThat(savedItemPrice.getId()).isNotNull();
        assertThat(savedItemPrice.getPrice()).isEqualTo(1500L);
    }

    @Test
    public void read() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle ("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(1500L);
        itemPrice.setIteminfo(iteminfo);

        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);
        Long id = savedItemPrice.getId();

        Optional<ItemPrice> foundItemPrice = itemPriceRepository.findById(id);

        assertThat(foundItemPrice.isPresent()).isTrue();
        assertThat(foundItemPrice.get().getId()).isEqualTo(id);
        assertThat(foundItemPrice.get().getPrice()).isEqualTo(1500L);
    }

    @Test
    public void update() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle ("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(1500L);
        itemPrice.setIteminfo(iteminfo);

        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);
        Long id = savedItemPrice.getId();

        ItemPrice foundItemPrice = itemPriceRepository.findById(id).get();
        foundItemPrice.setPrice(2000L);
        itemPriceRepository.save(foundItemPrice);

        ItemPrice updatedItemPrice = itemPriceRepository.findById(id).get();
        assertThat(updatedItemPrice.getId()).isEqualTo(id);
        assertThat(updatedItemPrice.getPrice()).isEqualTo(2000L);
    }

    @Test
    public void delete() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle ("testItem");
        iteminfo.setStartingPrice(1000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(1500L);
        itemPrice.setIteminfo(iteminfo);

        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);
        Long id = savedItemPrice.getId();

        itemPriceRepository.deleteById(id);

        Optional<ItemPrice> foundItemPrice = itemPriceRepository.findById(id);
        assertThat(foundItemPrice.isPresent()).isFalse();
    }

}