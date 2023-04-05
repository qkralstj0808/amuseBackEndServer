package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemTicket;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTicketRepositoryTest {

    @Autowired
    private ItemTicketRepository itemTicketRepository;

    @Test
    public void create() {
        ItemTicket itemTicket = new ItemTicket();
        itemTicket.setItemOptionTitle("Test Item Option Title");
        itemTicket.setItemOptionContent("Test Item Option Content");
        itemTicket.setPrice(1000L);

        ItemTicket savedItemTicket = itemTicketRepository.save(itemTicket);

        assertNotNull(savedItemTicket.getId());
        assertEquals(itemTicket.getItemOptionTitle(), savedItemTicket.getItemOptionTitle());
        assertEquals(itemTicket.getItemOptionContent(), savedItemTicket.getItemOptionContent());
        assertEquals(itemTicket.getPrice(), savedItemTicket.getPrice());
    }

    @Test
    public void read() {
        ItemTicket itemTicket = new ItemTicket();
        itemTicket.setItemOptionTitle("Test Item Option Title");
        itemTicket.setItemOptionContent("Test Item Option Content");
        itemTicket.setPrice(1000L);

        ItemTicket savedItemTicket = itemTicketRepository.save(itemTicket);

        Optional<ItemTicket> foundItemTicketOptional = itemTicketRepository.findById(savedItemTicket.getId());

        assertTrue(foundItemTicketOptional.isPresent());
        assertEquals(savedItemTicket.getId(), foundItemTicketOptional.get().getId());
        assertEquals(savedItemTicket.getItemOptionTitle(), foundItemTicketOptional.get().getItemOptionTitle());
        assertEquals(savedItemTicket.getItemOptionContent(), foundItemTicketOptional.get().getItemOptionContent());
        assertEquals(savedItemTicket.getPrice(), foundItemTicketOptional.get().getPrice());
    }

    @Test
    public void update() {
        ItemTicket itemTicket = new ItemTicket();
        itemTicket.setItemOptionTitle("Test Item Option Title");
        itemTicket.setItemOptionContent("Test Item Option Content");
        itemTicket.setPrice(1000L);

        ItemTicket savedItemTicket = itemTicketRepository.save(itemTicket);

        savedItemTicket.setItemOptionTitle("Updated Item Option Title");
        savedItemTicket.setItemOptionContent("Updated Item Option Content");
        savedItemTicket.setPrice(2000L);

        ItemTicket updatedItemTicket = itemTicketRepository.save(savedItemTicket);

        assertEquals(savedItemTicket.getId(), updatedItemTicket.getId());
        assertEquals(savedItemTicket.getItemOptionTitle(), updatedItemTicket.getItemOptionTitle());
        assertEquals(savedItemTicket.getItemOptionContent(), updatedItemTicket.getItemOptionContent());
        assertEquals(savedItemTicket.getPrice(), updatedItemTicket.getPrice());
    }

    @Test
    public void delete() {
        ItemTicket itemTicket = new ItemTicket();
        itemTicket.setItemOptionTitle("Test Item Option Title");
        itemTicket.setItemOptionContent("Test Item Option Content");
        itemTicket.setPrice(1000L);

        ItemTicket savedItemTicket = itemTicketRepository.save(itemTicket);

        itemTicketRepository.deleteById(savedItemTicket.getId());

        Optional<ItemTicket> foundItemTicketOptional = itemTicketRepository.findById(savedItemTicket.getId());

        assertFalse(foundItemTicketOptional.isPresent());
    }
}