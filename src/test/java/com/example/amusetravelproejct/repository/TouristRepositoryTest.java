package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Tourist;
import com.example.amusetravelproejct.domain.person_enum.Gender;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TouristRepositoryTest {

    @Autowired
    TouristRepository touristRepository;

    @Test
    void create_reade(){
        Tourist tourist = new Tourist();
        tourist.setTouristName("Test Tourist");
        tourist.setTouristSex(Gender.WOMAN);
        tourist.setIsBathchair(true);
        // Save Tourist
        touristRepository.save(tourist);
        // Verify the save
        Tourist savedTourist = touristRepository.findById(tourist.getId()).get();
        assertNotNull(savedTourist);
        assertEquals(tourist.getTouristName(), savedTourist.getTouristName());
        assertEquals(tourist.getTouristSex(), savedTourist.getTouristSex());
        assertEquals(tourist.getIsBathchair(), savedTourist.getIsBathchair());
    }
    @Test
    void update() {
        // Create new Tourist
        Tourist tourist = new Tourist();
        tourist.setTouristName("Test Tourist");
        tourist.setTouristSex(Gender.WOMAN);
        tourist.setIsBathchair(true);
        touristRepository.save(tourist);

        // Update Tourist
        tourist.setTouristName("Updated Tourist");
        touristRepository.save(tourist);

        // Verify the update
        Tourist updatedTourist = touristRepository.findById(tourist.getId()).get();
        assertEquals(tourist.getTouristName(), updatedTourist.getTouristName());
    }

    @Test
    public void delete() {
        // Create new Tourist
        Tourist tourist = new Tourist();
        tourist.setTouristName("Test Tourist");
        tourist.setTouristSex(Gender.WOMAN);
        tourist.setIsBathchair(true);
        touristRepository.save(tourist);

        // Delete Tourist
        touristRepository.delete(tourist);

        // Verify the delete
        Tourist deletedTourist = touristRepository.findById(tourist.getId()).orElse(null);
        assertNull(deletedTourist);
    }
}