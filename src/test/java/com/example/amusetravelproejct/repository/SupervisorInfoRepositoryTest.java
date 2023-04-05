package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.Iteminfo;
import com.example.amusetravelproejct.domain.SupervisorInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupervisorInfoRepositoryTest {


    @Autowired
    private SupervisorInfoRepository supervisorInfoRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private IteminfoRepository iteminfoRepository;

    @Test
    public void testSaveSupervisorInfo() {
        SupervisorInfo supervisorInfo = new SupervisorInfo();
        supervisorInfo.setIntroduction("This is a test introduction.");

        // create admin
        Admin admin = new Admin();
        admin.setEmail("admin@test.com");
        admin.setPassword("password");
        adminRepository.save(admin);

        // create iteminfo
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("Test Item");
        iteminfo.setMaxPrice(10000l);
        iteminfoRepository.save(iteminfo);

        supervisorInfo.setAdmin(admin);
        supervisorInfo.setIteminfo(iteminfo);
        supervisorInfoRepository.save(supervisorInfo);

        assertNotNull(supervisorInfo.getId());

        SupervisorInfo savedSupervisorInfo = supervisorInfoRepository.findById(supervisorInfo.getId()).orElse(null);
        assertNotNull(savedSupervisorInfo);
        assertEquals(supervisorInfo.getIntroduction(), savedSupervisorInfo.getIntroduction());
        assertEquals(supervisorInfo.getAdmin().getId(), savedSupervisorInfo.getAdmin().getId());
        assertEquals(supervisorInfo.getIteminfo().getId(), savedSupervisorInfo.getIteminfo().getId());
    }

    @Test
    public void testUpdateSupervisorInfo() {
        SupervisorInfo supervisorInfo = new SupervisorInfo();
        supervisorInfo.setIntroduction("This is a test introduction.");

        // create admin
        Admin admin = new Admin();
        admin.setEmail("admin@test.com");
        admin.setPassword("password");
        adminRepository.save(admin);

        // create iteminfo
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("Test Item");
        iteminfo.setMaxPrice(10000l);
        iteminfoRepository.save(iteminfo);

        supervisorInfo.setAdmin(admin);
        supervisorInfo.setIteminfo(iteminfo);

        supervisorInfoRepository.save(supervisorInfo);

        supervisorInfo.setIntroduction("Updated introduction.");
        supervisorInfoRepository.save(supervisorInfo);

        SupervisorInfo updatedSupervisorInfo = supervisorInfoRepository.findById(supervisorInfo.getId()).orElse(null);
        assertNotNull(updatedSupervisorInfo);
        assertEquals(supervisorInfo.getIntroduction(), updatedSupervisorInfo.getIntroduction());
    }

    @Test
    public void testDeleteSupervisorInfo() {
        SupervisorInfo supervisorInfo = new SupervisorInfo();
        supervisorInfo.setIntroduction("This is a test introduction.");

        // create admin
        Admin admin = new Admin();
        admin.setEmail("admin@test.com");
        admin.setPassword("password");
        adminRepository.save(admin);

        // create iteminfo
        Iteminfo iteminfo = new Iteminfo();
        iteminfo.setItemTitle("Test Item");
        iteminfo.setMaxPrice(10000l);
        iteminfoRepository.save(iteminfo);

        supervisorInfo.setAdmin(admin);
        supervisorInfo.setIteminfo(iteminfo);

        supervisorInfoRepository.save(supervisorInfo);

        supervisorInfoRepository.delete(supervisorInfo);

        SupervisorInfo deletedSupervisorInfo = supervisorInfoRepository.findById(supervisorInfo.getId()).orElse(null);
        assertNull(deletedSupervisorInfo);
    }
}