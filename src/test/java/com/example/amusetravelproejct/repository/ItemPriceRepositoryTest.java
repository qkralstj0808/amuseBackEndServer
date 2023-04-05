package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemPrice;
import com.example.amusetravelproejct.domain.Iteminfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ItemPriceRepositoryTest {

    @Autowired
    private ItemPriceRepository itemPriceRepository;

    @Test
    void create() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("Item 1");
        iteminfo.setMaxPrice(10000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(12000L);
        itemPrice.setIteminfo(iteminfo);

        // when
        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);

        // then
        assertNotNull(savedItemPrice.getId());
        assertEquals(itemPrice.getDate(), savedItemPrice.getDate());
        assertEquals(itemPrice.getPrice(), savedItemPrice.getPrice());
    }

    @Test
    void read() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("Item 1");
        iteminfo.setMaxPrice(10000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(12000L);
        itemPrice.setIteminfo(iteminfo);

        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);

        // when
        Optional<ItemPrice> foundItemPrice = itemPriceRepository.findById(savedItemPrice.getId());

        // then
        assertTrue(foundItemPrice.isPresent());
        assertEquals(savedItemPrice.getId(), foundItemPrice.get().getId());
        assertEquals(savedItemPrice.getDate(), foundItemPrice.get().getDate());
        assertEquals(savedItemPrice.getPrice(), foundItemPrice.get().getPrice());
    }

    @Test
    void deleteItemPriceTest() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("Item 1");
        iteminfo.setMaxPrice(10000L);

        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setDate(new Date());
        itemPrice.setPrice(12000L);
        itemPrice.setIteminfo(iteminfo);

        ItemPrice savedItemPrice = itemPriceRepository.save(itemPrice);

        // when
        itemPriceRepository.delete(savedItemPrice);

        // then
        assertFalse(itemPriceRepository.findById(savedItemPrice.getId()).isPresent());
    }

}