package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.controller.admin.dto.resp.AdvertisementPageResponse;
import com.example.amusetravelproejct.domain.AdminAdvertisement;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdminAdvertisementRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AdminAdvertisementService {
    private final AdminAdvertisementRepository adminAdvertisementRepository;

    public List<AdminAdvertisement> getAllAdvertisements() {
        List<AdminAdvertisement> advertisements = adminAdvertisementRepository.findAll();


        return adminAdvertisementRepository.findAll();
    }

    public Optional<AdminAdvertisement> getAdvertisementById(Long id) {
        return adminAdvertisementRepository.findById(id);
    }

    public AdminAdvertisement createAdvertisement(AdminAdvertisement advertisement) {

        return adminAdvertisementRepository.save(advertisement);
    }

    public AdminAdvertisement updateAdvertisement(AdminAdvertisement advertisement) {
        return adminAdvertisementRepository.save(advertisement);
    }

    public void deleteAdvertisement(Long id) {
        Optional<AdminAdvertisement> existingAdvertisement = adminAdvertisementRepository.findById(id);
        if (existingAdvertisement.isPresent()) {
            adminAdvertisementRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Advertisement not found with id " + id);
        }
    }
}
