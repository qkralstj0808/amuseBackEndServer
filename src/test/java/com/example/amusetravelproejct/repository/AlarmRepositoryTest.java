package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Alarm;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class AlarmRepositoryTest {

    @Autowired
    private AlarmRepository alarmRepository;

    @Test
    public void create() {
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setSubTitle("Test Subtitle");
        alarm.setContent("Test Content");
        alarm.setCreateDate(LocalDate.now());
        alarm.setUpdateDate(LocalDate.now());

        Alarm savedAlarm = alarmRepository.save(alarm);

        assertNotNull(savedAlarm.getId());
        assertEquals(alarm.getTitle(), savedAlarm.getTitle());
        assertEquals(alarm.getSubTitle(), savedAlarm.getSubTitle());
        assertEquals(alarm.getContent(), savedAlarm.getContent());
        assertEquals(alarm.getCreateDate(), savedAlarm.getCreateDate());
        assertEquals(alarm.getUpdateDate(), savedAlarm.getUpdateDate());
    }

    @Test
    public void read() {
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setSubTitle("Test Subtitle");
        alarm.setContent("Test Content");
        alarm.setCreateDate(LocalDate.now());
        alarm.setUpdateDate(LocalDate.now());

        Alarm savedAlarm = alarmRepository.save(alarm);
        Long id = savedAlarm.getId();

        Optional<Alarm> optionalAlarm = alarmRepository.findById(id);

        assertTrue(optionalAlarm.isPresent());
        assertEquals(alarm.getTitle(), optionalAlarm.get().getTitle());
        assertEquals(alarm.getSubTitle(), optionalAlarm.get().getSubTitle());
        assertEquals(alarm.getContent(), optionalAlarm.get().getContent());
        assertEquals(alarm.getCreateDate(), optionalAlarm.get().getCreateDate());
        assertEquals(alarm.getUpdateDate(), optionalAlarm.get().getUpdateDate());
    }

    @Test
    public void update() {
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setSubTitle("Test Subtitle");
        alarm.setContent("Test Content");
        alarm.setCreateDate(LocalDate.now());
        alarm.setUpdateDate(LocalDate.now());

        Alarm savedAlarm = alarmRepository.save(alarm);
        Long id = savedAlarm.getId();

        Optional<Alarm> optionalAlarm = alarmRepository.findById(id);
        assertTrue(optionalAlarm.isPresent());

        Alarm updatedAlarm = optionalAlarm.get();
        updatedAlarm.setTitle("Updated Alarm");
        updatedAlarm.setSubTitle("Updated Subtitle");
        updatedAlarm.setContent("Updated Content");
        updatedAlarm.setUpdateDate(LocalDate.now().plusDays(1));

        alarmRepository.save(updatedAlarm);

        Optional<Alarm> optionalUpdatedAlarm = alarmRepository.findById(id);
        assertTrue(optionalUpdatedAlarm.isPresent());

        Alarm finalUpdatedAlarm = optionalUpdatedAlarm.get();
        assertEquals(updatedAlarm.getTitle(), finalUpdatedAlarm.getTitle());
        assertEquals(updatedAlarm.getSubTitle(), finalUpdatedAlarm.getSubTitle());
        assertEquals(updatedAlarm.getContent(), finalUpdatedAlarm.getContent());
        assertEquals(updatedAlarm.getCreateDate(), finalUpdatedAlarm.getCreateDate());
        assertEquals(updatedAlarm.getUpdateDate(), finalUpdatedAlarm.getUpdateDate());
    }

    @Test
    public void delete() {
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setSubTitle("Test Subtitle");
        alarm.setContent("Test Content");
        alarm.setCreateDate(LocalDate.now());
        alarm.setUpdateDate(LocalDate.now());

        Alarm savedAlarm = alarmRepository.save(alarm);
        Long id = savedAlarm.getId();

        Optional<Alarm> optionalAlarm = alarmRepository.findById(id);
        assertTrue(optionalAlarm.isPresent());

        alarmRepository.delete(savedAlarm);

        Optional<Alarm> optionalDeletedAlarm = alarmRepository.findById(id);
        assertFalse(optionalDeletedAlarm.isPresent());
    }
}