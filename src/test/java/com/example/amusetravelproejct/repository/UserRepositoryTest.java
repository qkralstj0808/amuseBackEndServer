package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.person_enum.Gender;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setNickName("TestUser");
        user.setProfileImgLink("http://test.com/profile.jpg");
        user.setEmail("testuser@test.com");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        user.setGender(Gender.WOMAN);
        user.setEmailReceptionTrue(true);
        user.setMessageReceptionTrue(true);
        user.setIsMember(true);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getNickName(), savedUser.getNickName());
        assertEquals(user.getProfileImgLink(), savedUser.getProfileImgLink());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getBirthday(), savedUser.getBirthday());
        assertEquals(user.getGender(), savedUser.getGender());
        assertEquals(user.getEmailReceptionTrue(), savedUser.getEmailReceptionTrue());
        assertEquals(user.getMessageReceptionTrue(), savedUser.getMessageReceptionTrue());
        assertEquals(user.getIsMember(), savedUser.getIsMember());
    }

    @Test
    public void reade() {
        User user = new User();
        user.setNickName("TestUser");
        user.setProfileImgLink("http://test.com/profile.jpg");
        user.setEmail("testuser@test.com");
        user.setName("Test User");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        user.setGender(Gender.WOMAN);
        user.setEmailReceptionTrue(true);
        user.setMessageReceptionTrue(true);
        user.setIsMember(true);
        User savedUser = userRepository.save(user);

        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(user.getNickName(), foundUser.getNickName());
        assertEquals(user.getProfileImgLink(), foundUser.getProfileImgLink());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getBirthday(), foundUser.getBirthday());
        assertEquals(user.getGender(), foundUser.getGender());
        assertEquals(user.getEmailReceptionTrue(), foundUser.getEmailReceptionTrue());
        assertEquals(user.getMessageReceptionTrue(), foundUser.getMessageReceptionTrue());
        assertEquals(user.getIsMember(), foundUser.getIsMember());
    }

    // Update test
    @Test
    public void update() {
        // create new user
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("Test User");
        user.setNickName("testNick");
        user.setPassword("password");
        user.setProfileImgLink("http://test.com/profile.jpg");
        user.setGender(Gender.WOMAN);
        user.setEmailReceptionTrue(true);
        user.setMessageReceptionTrue(true);
        user.setIsMember(false);
        userRepository.save(user);

        // update user's name
        User savedUser = userRepository.findByEmail("test@test.com");
        savedUser.setName("New Test User");
        userRepository.save(savedUser);

        // verify the update
        User updatedUser = userRepository.findByEmail("test@test.com");
        assertEquals("New Test User", updatedUser.getName());
    }


    // Delete test
    @Test
    public void delete() {
        userRepository.deleteAll();
        // create new user
        User user = new User();
        user.setEmail("test@test.com");
        user.setName("Test User");
        user.setNickName("testNick");
        user.setPassword("password");
        user.setProfileImgLink("http://test.com/profile.jpg");
        user.setGender(Gender.WOMAN);
        user.setEmailReceptionTrue(true);
        user.setMessageReceptionTrue(true);
        user.setIsMember(false);
        userRepository.save(user);
        // delete user
        User savedUser = userRepository.findFirstByEmail("test@test.com");
        userRepository.delete(savedUser);
        userRepository.flush();


        // verify the delete
        User deletedUser = userRepository.findFirstByEmail("test@test.com");
        assertNull(deletedUser);
    }
}