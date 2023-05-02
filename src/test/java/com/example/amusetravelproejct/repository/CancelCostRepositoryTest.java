package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.CancelCost;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

class CancelCostRepositoryTest {


    @Autowired
    private CancelCostRepository cancelCostRepository;

    @Test
    public void create() {
        // create a new CancelCost object
        CancelCost cancelCost = new CancelCost();
        cancelCost.setBeforeStartDate(5L);
        cancelCost.setPanelty(100);

        // save the object using the repository
        CancelCost savedCancelCost = cancelCostRepository.save(cancelCost);

        // check that the saved object has an ID assigned
        assertNotNull(savedCancelCost.getId());
    }

    @Test
    public void read() {
        // create a new CancelCost object and save it
        CancelCost cancelCost = new CancelCost();
        cancelCost.setBeforeStartDate(5L);
        cancelCost.setPanelty(100);
        cancelCostRepository.save(cancelCost);

        // find the saved object by ID using the repository
        Optional<CancelCost> foundCancelCost = cancelCostRepository.findById(cancelCost.getId());

        // check that the found object matches the original object
        assertTrue(foundCancelCost.isPresent());
        assertEquals(cancelCost.getBeforeStartDate(), foundCancelCost.get().getBeforeStartDate());
        assertEquals(cancelCost.getPanelty(), foundCancelCost.get().getPanelty());
    }

    @Test
    public void update() {
        // create a new CancelCost object and save it
        CancelCost cancelCost = new CancelCost();
        cancelCost.setBeforeStartDate(5L);
        cancelCost.setPanelty(100);
        cancelCostRepository.save(cancelCost);

        // update the saved object
        cancelCost.setBeforeStartDate(10L);
        cancelCost.setPanelty(200);
        CancelCost updatedCancelCost = cancelCostRepository.save(cancelCost);

        // find the updated object by ID using the repository
        Optional<CancelCost> foundCancelCost = cancelCostRepository.findById(cancelCost.getId());

        // check that the found object matches the updated object
        assertTrue(foundCancelCost.isPresent());
        assertEquals(updatedCancelCost.getBeforeStartDate(), foundCancelCost.get().getBeforeStartDate());
        assertEquals(updatedCancelCost.getPanelty(), foundCancelCost.get().getPanelty());
    }

    @Test
    public void delete() {
        // create a new CancelCost object and save it
        CancelCost cancelCost = new CancelCost();
        cancelCost.setBeforeStartDate(5L);
        cancelCost.setPanelty(100);
        cancelCostRepository.save(cancelCost);

        // delete the saved object using the repository
        cancelCostRepository.delete(cancelCost);

        // try to find the deleted object by ID using the repository
        Optional<CancelCost> foundCancelCost = cancelCostRepository.findById(cancelCost.getId());

        // check that the deleted object cannot be found
        assertFalse(foundCancelCost.isPresent());
    }

}