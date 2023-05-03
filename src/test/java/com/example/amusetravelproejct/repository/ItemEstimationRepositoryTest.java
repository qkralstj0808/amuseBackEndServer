package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemEstimation;
<<<<<<< HEAD
import com.example.amusetravelproejct.domain.User;
=======
>>>>>>> develop
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD

=======
>>>>>>> develop

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

class ItemEstimationRepositoryTest {

    @Autowired
    private ItemEstimationRepository itemEstimationRepository;

    @Autowired
    private IteminfoRepository iteminfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateItemEstimation() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        User user = new User();
        user.setName("john_doe");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        ItemEstimation itemEstimation = new ItemEstimation();
        itemEstimation.setRating(4L);
        itemEstimation.setReviewContent("Great product!");
        itemEstimation.setIteminfo(savedIteminfo);
        itemEstimation.setUser(savedUser);
        ItemEstimation savedItemEstimation = itemEstimationRepository.save(itemEstimation);

        assertNotNull(savedItemEstimation.getId());
    }

    @Test
    public void testFindItemEstimationById() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        Iteminfo savedIteminfo = iteminfoRepository.save(iteminfo);

        User user = new User();
        user.setName("john_doe");
        user.setPassword("password");
        User savedUser = userRepository.save(user);

        ItemEstimation itemEstimation = new ItemEstimation();
        itemEstimation.setRating(4L);
        itemEstimation.setReviewContent("Great product!");
        itemEstimation.setIteminfo(savedIteminfo);
        itemEstimation.setUser(savedUser);
        ItemEstimation savedItemEstimation = itemEstimationRepository.save(itemEstimation);

        ItemEstimation foundItemEstimation = itemEstimationRepository.findById(savedItemEstimation.getId()).orElse(null);

        assertNotNull(foundItemEstimation);
        assertEquals(savedItemEstimation.getRating(), foundItemEstimation.getRating());
        assertEquals(savedItemEstimation.getReviewContent(), foundItemEstimation.getReviewContent());
        assertEquals(savedItemEstimation.getIteminfo().getId(), foundItemEstimation.getIteminfo().getId());
        assertEquals(savedItemEstimation.getUser().getId(), foundItemEstimation.getUser().getId());
    }

    @Test
    public void testUpdateItemEstimation() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);

        ItemEstimation itemEstimation = new ItemEstimation();
        itemEstimation.setRating(5L);
        itemEstimation.setReviewContent("This is a great item!");
        itemEstimation.setIteminfo(iteminfo);

        ItemEstimation savedItemEstimation = itemEstimationRepository.save(itemEstimation);

        // when
        savedItemEstimation.setRating(4L);
        ItemEstimation updatedItemEstimation = itemEstimationRepository.save(savedItemEstimation);

        // then
        assertThat(updatedItemEstimation.getRating()).isEqualTo(4L);
    }

    @Test
    public void testDeleteItemEstimation() {
        // given
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM002");
        iteminfo.setItemTitle("Item 2");
        iteminfo.setStartingPrice(2000L);

        ItemEstimation itemEstimation = new ItemEstimation();
        itemEstimation.setRating(3L);
        itemEstimation.setReviewContent("This item is average");
        itemEstimation.setIteminfo(iteminfo);

        ItemEstimation savedItemEstimation = itemEstimationRepository.save(itemEstimation);

        // when
        itemEstimationRepository.delete(savedItemEstimation);

        // then
        assertThat(itemEstimationRepository.findById(savedItemEstimation.getId())).isEmpty();
    }
}