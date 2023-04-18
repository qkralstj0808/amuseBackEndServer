package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.EstimateContact;
import com.example.amusetravelproejct.domain.Iteminfo;
import com.example.amusetravelproejct.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class EstimateContactRepositoryTest {

    @Autowired
    private EstimateContactRepository estimateContactRepository;

    @Autowired
    private IteminfoRepository iteminfoRepository;

    @Autowired
    private UserRepository userRepository;

    private Iteminfo createIteminfo() {
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemCode("ITEM001");
        iteminfo.setItemTitle("Item 1");
        iteminfo.setStartingPrice(1000L);
        return iteminfoRepository.save(iteminfo);
    }

    private User createUser() {
        User user = new User();
        user.setName("user1");
        user.setPassword("password1");
        return userRepository.save(user);
    }

    @Test
    public void create() {
        // given
        Iteminfo iteminfo = createIteminfo();
        User user = createUser();
        EstimateContact estimateContact = new EstimateContact();
        estimateContact.setContent("test content");
        estimateContact.setIteminfo(iteminfo);
        estimateContact.setUser(user);

        // when
        EstimateContact savedEstimateContact = estimateContactRepository.save(estimateContact);

        // then
        assertThat(savedEstimateContact.getId()).isNotNull();
        assertThat(savedEstimateContact.getContent()).isEqualTo("test content");
        assertThat(savedEstimateContact.getIteminfo().getId()).isEqualTo(iteminfo.getId());
        assertThat(savedEstimateContact.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void read() {
        // given
        Iteminfo iteminfo = createIteminfo();
        User user = createUser();
        EstimateContact estimateContact = new EstimateContact();
        estimateContact.setContent("test content");
        estimateContact.setIteminfo(iteminfo);
        estimateContact.setUser(user);
        EstimateContact savedEstimateContact = estimateContactRepository.save(estimateContact);

        // when
        Optional<EstimateContact> foundEstimateContactOptional = estimateContactRepository.findById(savedEstimateContact.getId());

        // then
        assertThat(foundEstimateContactOptional.isPresent()).isTrue();
        EstimateContact foundEstimateContact = foundEstimateContactOptional.get();
        assertThat(foundEstimateContact.getId()).isEqualTo(savedEstimateContact.getId());
        assertThat(foundEstimateContact.getContent()).isEqualTo(savedEstimateContact.getContent());
        assertThat(foundEstimateContact.getIteminfo().getId()).isEqualTo(savedEstimateContact.getIteminfo().getId());
        assertThat(foundEstimateContact.getUser().getId()).isEqualTo(savedEstimateContact.getUser().getId());
    }

    @Test
    public void update() {
        // given
        Iteminfo iteminfo = createIteminfo();
        User user = createUser();
        EstimateContact estimateContact = new EstimateContact();
        estimateContact.setContent("Test content");
        estimateContact.setIteminfo(iteminfo);
        estimateContact.setUser(user);
        estimateContactRepository.save(estimateContact);

        // when
        estimateContact.setContent("Updated content");
        estimateContactRepository.save(estimateContact);

        // then
        Optional<EstimateContact> foundEstimateContact = estimateContactRepository.findById(estimateContact.getId());
        assertThat(foundEstimateContact).isPresent();
        assertThat(foundEstimateContact.get().getContent()).isEqualTo("Updated content");
    }

    @Test
    public void delete() {
        // given
        Iteminfo iteminfo = createIteminfo();
        User user = createUser();
        EstimateContact estimateContact = new EstimateContact();
        estimateContact.setContent("Test content");
        estimateContact.setIteminfo(iteminfo);
        estimateContact.setUser(user);
        estimateContactRepository.save(estimateContact);

        // when
        estimateContactRepository.deleteById(estimateContact.getId());

        // then
        assertThat(estimateContactRepository.findById(estimateContact.getId())).isNotPresent();
    }
}