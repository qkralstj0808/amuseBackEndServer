package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.AdminAdvertisement;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

class AdminAdvertisementRepositoryTest {

    @Autowired
    private AdminAdvertisementRepository adminAdvertisementRepository;

    @Test
    public void create() {
        // create a new admin advertisement
        AdminAdvertisement adminAdvertisement = new AdminAdvertisement();
        adminAdvertisement.setAdvertisementTitle("New Advertisement");
        adminAdvertisement.setAdvertisementContent("This is a new advertisement");
        adminAdvertisement.setAdvertisementStartDate(LocalDateTime.now());
        adminAdvertisement.setAdvertisementEndDate(LocalDateTime.now().plusDays(7));

        // save the admin advertisement to the database
        AdminAdvertisement savedAdminAdvertisement = adminAdvertisementRepository.save(adminAdvertisement);

        // verify that the admin advertisement has been saved successfully
        assertNotNull(savedAdminAdvertisement.getId());
        assertEquals(adminAdvertisement.getAdvertisementTitle(), savedAdminAdvertisement.getAdvertisementTitle());
        assertEquals(adminAdvertisement.getAdvertisementContent(), savedAdminAdvertisement.getAdvertisementContent());
        assertEquals(adminAdvertisement.getAdvertisementStartDate(), savedAdminAdvertisement.getAdvertisementStartDate());
        assertEquals(adminAdvertisement.getAdvertisementEndDate(), savedAdminAdvertisement.getAdvertisementEndDate());
    }
    @Test
    public void read() {
        // Given
        AdminAdvertisement advertisement = new AdminAdvertisement();
        advertisement.setAdvertisementTitle("Test Ad");
        advertisement.setAdvertisementContent("This is a test advertisement.");
        advertisement.setAdvertisementStartDate(LocalDateTime.now());
        advertisement.setAdvertisementEndDate(LocalDateTime.now().plusDays(7));
        adminAdvertisementRepository.save(advertisement);

        // When
        AdminAdvertisement foundAdvertisement = adminAdvertisementRepository.findById(advertisement.getId()).orElse(null);

        // Then
        assertThat(foundAdvertisement).isNotNull();
        assertThat(foundAdvertisement.getAdvertisementTitle()).isEqualTo(advertisement.getAdvertisementTitle());
        assertThat(foundAdvertisement.getAdvertisementContent()).isEqualTo(advertisement.getAdvertisementContent());
        assertThat(foundAdvertisement.getAdvertisementStartDate()).isEqualTo(advertisement.getAdvertisementStartDate());
        assertThat(foundAdvertisement.getAdvertisementEndDate()).isEqualTo(advertisement.getAdvertisementEndDate());
    }
    @Test
    public void update() {
        // create a new admin advertisement
        AdminAdvertisement adminAdvertisement = new AdminAdvertisement();
        adminAdvertisement.setAdvertisementTitle("New Advertisement");
        adminAdvertisement.setAdvertisementContent("This is a new advertisement");
        adminAdvertisement.setAdvertisementStartDate(LocalDateTime.now());
        adminAdvertisement.setAdvertisementEndDate(LocalDateTime.now().plusDays(7));

        // save the admin advertisement to the database
        AdminAdvertisement savedAdminAdvertisement = adminAdvertisementRepository.save(adminAdvertisement);

        // update the admin advertisement
        savedAdminAdvertisement.setAdvertisementTitle("Updated Advertisement Title");
        savedAdminAdvertisement.setAdvertisementContent("This is an updated advertisement");

        // save the updated admin advertisement to the database
        AdminAdvertisement updatedAdminAdvertisement = adminAdvertisementRepository.save(savedAdminAdvertisement);

        // verify that the admin advertisement has been updated successfully
        assertEquals(savedAdminAdvertisement.getId(), updatedAdminAdvertisement.getId());
        assertEquals(savedAdminAdvertisement.getAdvertisementStartDate(), updatedAdminAdvertisement.getAdvertisementStartDate());
        assertEquals(savedAdminAdvertisement.getAdvertisementEndDate(), updatedAdminAdvertisement.getAdvertisementEndDate());
        assertEquals("Updated Advertisement Title", updatedAdminAdvertisement.getAdvertisementTitle());
        assertEquals("This is an updated advertisement", updatedAdminAdvertisement.getAdvertisementContent());
    }

    @Test
    public void delete() {
        // create a new admin advertisement
        AdminAdvertisement adminAdvertisement = new AdminAdvertisement();
        adminAdvertisement.setAdvertisementTitle("New Advertisement");
        adminAdvertisement.setAdvertisementContent("This is a new advertisement");
        adminAdvertisement.setAdvertisementStartDate(LocalDateTime.now());
        adminAdvertisement.setAdvertisementEndDate(LocalDateTime.now().plusDays(7));

        // save the admin advertisement to the database
        AdminAdvertisement savedAdminAdvertisement = adminAdvertisementRepository.save(adminAdvertisement);

        // delete the admin advertisement from the database
        adminAdvertisementRepository.delete(savedAdminAdvertisement);

        // verify that the admin advertisement has been deleted successfully
        assertFalse(adminAdvertisementRepository.findById(savedAdminAdvertisement.getId()).isPresent());
    }

}