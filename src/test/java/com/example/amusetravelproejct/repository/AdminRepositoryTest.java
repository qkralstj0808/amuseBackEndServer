package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Admin;
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
class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void create() {
        Admin admin = new Admin();
        admin.setEmail("testadmin@test.com");
        admin.setPassword("password");
        admin.setName("Test Admin");
        admin.setProfileImgLink("http://example.com/profile.png");

        adminRepository.save(admin);

        assertThat(admin.getId()).isNotNull();
    }

    @Test
    void read() {
        Admin admin = new Admin();
        admin.setEmail("testadmin@test.com");
        admin.setPassword("password");
        admin.setName("Test Admin");
        admin.setProfileImgLink("http://example.com/profile.png");

        adminRepository.save(admin);

        Admin retrievedAdmin = adminRepository.findById(admin.getId()).orElse(null);
        assertThat(retrievedAdmin).isNotNull();
        assertThat(retrievedAdmin.getName()).isEqualTo("Test Admin");
    }

    @Test
    void update() {
        Admin admin = new Admin();
        admin.setEmail("testadmin@test.com");
        admin.setPassword("password");
        admin.setName("Test Admin");
        admin.setProfileImgLink("http://example.com/profile.png");

        adminRepository.save(admin);

        admin.setName("Updated Admin");
        adminRepository.save(admin);

        Admin retrievedAdmin = adminRepository.findById(admin.getId()).orElse(null);
        assertThat(retrievedAdmin).isNotNull();
        assertThat(retrievedAdmin.getName()).isEqualTo("Updated Admin");
    }

    @Test
    void delete() {
        Admin admin = new Admin();
        admin.setEmail("testadmin@test.com");
        admin.setPassword("password");
        admin.setName("Test Admin");
        admin.setProfileImgLink("http://example.com/profile.png");

        adminRepository.save(admin);

        adminRepository.delete(admin);

        Admin retrievedAdmin = adminRepository.findById(admin.getId()).orElse(null);
        assertThat(retrievedAdmin).isNull();
    }
}