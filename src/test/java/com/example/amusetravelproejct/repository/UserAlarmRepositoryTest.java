package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Alarm;
import com.example.amusetravelproejct.domain.UserAlarm;
import com.example.amusetravelproejct.domain.person_enum.Gender;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserAlarmRepositoryTest {


    @Autowired
    private UserAlarmRepository userAlarmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private EntityManager entityManager;
    private static User user;
    private static Alarm alarm;

    @Before
    public void setup() {

        // Create user
        user = new User();
        user.setEmail("test@test.com");
        user.setName("Test User");
        user.setNickName("testNick");
        user.setPassword("password");
        user.setProfileImgLink("http://test.com/profile.jpg");
        user.setGender(Gender.WOMAN);
        user.setEmailReceptionTrue(true);
        user.setMessageReceptionTrue(true);
        user.setIsMember(false);

        entityManager.persist(user);
        // Create alarm
        alarm = new Alarm();
        alarm.setContent("This is a test alarm.");
        entityManager.persist(alarm);
    }


    @Test
    public void create() {
        // Create user
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
        // Create alarm
        Alarm alarm = new Alarm();
        alarm.setContent("This is a test alarm.");

        alarmRepository.save(alarm);

        // Create new UserAlarm
        UserAlarm userAlarm = new UserAlarm();
        userAlarm.setStatus(true);
        userAlarm.setUser(user);
        userAlarm.setAlarm(alarm);

        // Save UserAlarm
        userAlarmRepository.save(userAlarm);

        // Verify the save
        UserAlarm savedUserAlarm = entityManager.find(UserAlarm.class, userAlarm.getId());
        assertNotNull(savedUserAlarm);
        assertEquals(userAlarm.getStatus(), savedUserAlarm.getStatus());
        assertNotNull(userAlarm.getUser());
        assertEquals(userAlarm.getUser().getId(), savedUserAlarm.getUser().getId());
        assertEquals(userAlarm.getAlarm().getId(), savedUserAlarm.getAlarm().getId());
    }

    @Test
    public void update() {
        // Create new UserAlarm
        UserAlarm userAlarm = new UserAlarm();
        userAlarm.setStatus(true);
        userAlarm.setUser(user);
        userAlarm.setAlarm(alarm);
        userAlarmRepository.save(userAlarm);

        // Update UserAlarm
        userAlarm.setStatus(false);
        userAlarmRepository.save(userAlarm);

        // Verify the update
        UserAlarm updatedUserAlarm = userAlarmRepository.findById(userAlarm.getId()).get();
        assertEquals(userAlarm.getStatus(), updatedUserAlarm.getStatus());
    }

    @Test
    public void delete() {
        // Create new UserAlarm
        UserAlarm userAlarm = new UserAlarm();
        userAlarm.setStatus(true);
        userAlarm.setUser(user);
        userAlarm.setAlarm(alarm);
        userAlarmRepository.save(userAlarm);

        // Delete UserAlarm
        userAlarmRepository.delete(userAlarm);

        // Verify the delete
        UserAlarm deletedUserAlarm = userAlarmRepository.findById(userAlarm.getId()).orElse(null);
        assertNull(deletedUserAlarm);
    }


}