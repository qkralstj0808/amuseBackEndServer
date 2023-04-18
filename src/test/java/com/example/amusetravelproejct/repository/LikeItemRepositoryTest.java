package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Iteminfo;
import com.example.amusetravelproejct.domain.LikeItem;
import com.example.amusetravelproejct.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class LikeItemRepositoryTest {

    @Autowired
    private LikeItemRepository likeItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IteminfoRepository iteminfoRepository;
    @Test
    void create() {
        // create test data
        User user = new User();
//        user.setName("test_user");

        userRepository.save(user);

        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("test_item");

        iteminfoRepository.save(iteminfo);


        LikeItem likeItem = new LikeItem();
        likeItem.setUser(user);
        likeItem.setIteminfo(iteminfo);


        // save and flush the entity
        LikeItem savedLikeItem = likeItemRepository.save(likeItem);


        // verify the saved entity
        assertThat(savedLikeItem.getId()).isNotNull();
//        assertThat(savedLikeItem.getUser().getName()).isEqualTo(user.getName());
        assertThat(savedLikeItem.getIteminfo().getItemTitle()).isEqualTo(iteminfo.getItemTitle());
    }

    @Test
    void delete() {
        // create test data
        User user = new User();
        user.setName("test_user");

        userRepository.save(user);

        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("test_item");

        iteminfoRepository.save(iteminfo);
        LikeItem likeItem = new LikeItem();
        likeItem.setUser(user);
        likeItem.setIteminfo(iteminfo);

        likeItemRepository.save(likeItem);

        // delete the entity
        likeItemRepository.delete(likeItem);

        // verify the entity has been deleted
        assertThat(likeItemRepository.findById(likeItem.getId())).isEmpty();
    }

    @Test
    void read_list_likeItem() {
        // create test data
        likeItemRepository.deleteAll();

        User user = new User();
        user.setName("test_user");

        userRepository.save(user);

        Iteminfo iteminfo1 = new Iteminfo();
        iteminfo1.setItemTitle ("test_item1");

        iteminfoRepository.save(iteminfo1);


        Iteminfo iteminfo2 = new Iteminfo();
        iteminfo2.setItemTitle("test_item2");

        iteminfoRepository.save(iteminfo2);

        LikeItem likeItem1 = new LikeItem();
        likeItem1.setUser(user);
        likeItem1.setIteminfo(iteminfo1);

        likeItemRepository.save(likeItem1);

        LikeItem likeItem2 = new LikeItem();
        likeItem2.setUser(user);
        likeItem2.setIteminfo(iteminfo2);

        likeItemRepository.save(likeItem2);


        // find the entities by user
        List<LikeItem> foundLikeItems = likeItemRepository.findByUser(user);

        // verify the found entities
        Assertions.assertThat(foundLikeItems.get(0).getId()).isEqualTo(likeItem1.getId());
        Assertions.assertThat(foundLikeItems.get(1).getId()).isEqualTo(likeItem2.getId());
    }
}