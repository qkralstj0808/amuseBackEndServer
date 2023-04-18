package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.CustomerQuestion;
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

class CustomerQuestionRepositoryTest {
    @Autowired
    private CustomerQuestionRepository customerQuestionRepository;

    @Test
    public void create() {
        // Given
        CustomerQuestion question = new CustomerQuestion();
        question.setTitle("Test Title");
        question.setSubTitle("Test Subtitle");
        question.setContents("Test Contents");
        question.setCreateDate(LocalDate.now());
        question.setUpdateDate(LocalDate.now());
        question.setCompleteTrue(false);

        // When
        customerQuestionRepository.save(question);

        // Then
        assertNotNull(question.getId());
    }

    @Test
    public void read() {
        // Given
        CustomerQuestion question = new CustomerQuestion();
        question.setTitle("Test Title");
        question.setSubTitle("Test Subtitle");
        question.setContents("Test Contents");
        question.setCreateDate(LocalDate.now());
        question.setUpdateDate(LocalDate.now());
        question.setCompleteTrue(false);
        customerQuestionRepository.save(question);

        // When
        Optional<CustomerQuestion> foundQuestion = customerQuestionRepository.findById(question.getId());

        // Then
        assertTrue(foundQuestion.isPresent());
        assertEquals("Test Title", foundQuestion.get().getTitle());
        assertEquals("Test Subtitle", foundQuestion.get().getSubTitle());
        assertEquals("Test Contents", foundQuestion.get().getContents());
        assertFalse(foundQuestion.get().getCompleteTrue());
    }

    @Test
    public void update() {
        // Given
        CustomerQuestion question = new CustomerQuestion();
        question.setTitle("Test Title");
        question.setSubTitle("Test Subtitle");
        question.setContents("Test Contents");
        question.setCreateDate(LocalDate.now());
        question.setUpdateDate(LocalDate.now());
        question.setCompleteTrue(false);
        customerQuestionRepository.save(question);

        // When
        question.setTitle("Updated Title");
        customerQuestionRepository.save(question);

        // Then
        Optional<CustomerQuestion> foundQuestion = customerQuestionRepository.findById(question.getId());
        assertTrue(foundQuestion.isPresent());
        assertEquals("Updated Title", foundQuestion.get().getTitle());
    }

    @Test
    public void delete() {
        // Given
        CustomerQuestion question = new CustomerQuestion();
        question.setTitle("Test Title");
        question.setSubTitle("Test Subtitle");
        question.setContents("Test Contents");
        question.setCreateDate(LocalDate.now());
        question.setUpdateDate(LocalDate.now());
        question.setCompleteTrue(false);
        customerQuestionRepository.save(question);

        // When
        customerQuestionRepository.delete(question);

        // Then
        assertFalse(customerQuestionRepository.findById(question.getId()).isPresent());
    }

}