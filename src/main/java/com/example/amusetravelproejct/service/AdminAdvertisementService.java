package com.example.amusetravelproejct.service;


import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.Advertisement;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdvertisementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AdminAdvertisementService {
    private final AdvertisementRepository adminAdvertisementRepository;


    public AdminPageResponse.advertisementRegister  processAdvertisementRegister(AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto , CategoryService categoryService, AdminService adminService, UtilMethod utilMethod) {

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(adminAdvertisementRegisterDto.getTitle());
        advertisement.setContent(adminAdvertisementRegisterDto.getAdContent());
        advertisement.setStartDate(Date.valueOf(adminAdvertisementRegisterDto.getStartDate()));
        advertisement.setEndDate(Date.valueOf(adminAdvertisementRegisterDto.getEndDate()));
        advertisement.setAdmin(adminService.getAdminByEmail(adminAdvertisementRegisterDto.getCreatedBy()).get());
//        advertisement.setCategory(categoryService.getCategoryByName(adminAdvertisementRegisterDto.getAdCategory()).get());
        advertisement.setPcBannerUrl(utilMethod.getImgUrl(adminAdvertisementRegisterDto.getPcBannerBase64(), adminAdvertisementRegisterDto.getPcBannerFileName()));
        advertisement.setMobileBannerUrl(utilMethod.getImgUrl(adminAdvertisementRegisterDto.getMobileBannerBase64(), adminAdvertisementRegisterDto.getMobileBannerFileName()));
        advertisement.setPcBannerLink(adminAdvertisementRegisterDto.getPcBannerLink());
        advertisement.setMobileBannerLink(adminAdvertisementRegisterDto.getMobileBannerLink());
        adminAdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementRegister advertisementRegister = new AdminPageResponse.advertisementRegister();

        advertisementRegister.setId(advertisement.getId());
        advertisementRegister.setTitle(advertisement.getTitle());
        advertisementRegister.setStartDate(advertisement.getStartDate().toString());
        advertisementRegister.setEndDate(advertisement.getEndDate().toString());
//        advertisementRegister.setAdCategory(advertisement.getCategory().getCategoryName());
        advertisementRegister.setAdContent(advertisement.getContent());
        advertisementRegister.setCreatedBy(advertisement.getAdmin().getEmail());
        advertisementRegister.setPcBannerUrl(advertisement.getPcBannerUrl());
        advertisementRegister.setMobileBannerUrl(advertisement.getMobileBannerUrl());
        advertisementRegister.setPcBannerLink(advertisement.getPcBannerLink());
        advertisementRegister.setMobileBannerLink(advertisement.getMobileBannerLink());


        return advertisementRegister;
    }


    public AdminPageResponse.advertisementEdit processAdvertisementEdit(AdminPageRequest.advertisementEdit advertisementEditDto, CategoryService categoryService, AdminService adminService, UtilMethod utilMethod) {
        Advertisement advertisement = adminAdvertisementRepository.findById(advertisementEditDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Advertisement not Found"));

        advertisement.setTitle(advertisementEditDto.getTitle());
        advertisement.setContent(advertisementEditDto.getAdContent());
        advertisement.setStartDate(Date.valueOf(advertisementEditDto.getStartDate()));
        advertisement.setEndDate(Date.valueOf(advertisementEditDto.getEndDate()));
        advertisement.setAdmin(adminService.getAdminByEmail(advertisementEditDto.getCreatedBy()).get());
//        advertisement.setCategory(categoryService.getCategoryByName(advertisementEditDto.getAdCategory()).get());
        advertisement.setPcBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getPcBannerBase64(), advertisementEditDto.getPcBannerFileName()));
        advertisement.setMobileBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getMobileBannerBase64(), advertisementEditDto.getMobileBannerFileName()));
        advertisement.setPcBannerLink(advertisementEditDto.getPcBannerLink());
        advertisement.setMobileBannerLink(advertisementEditDto.getMobileBannerLink());
        adminAdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementEdit advertisementEdit = new AdminPageResponse.advertisementEdit();

        advertisementEdit.setId(advertisement.getId());
        advertisementEdit.setTitle(advertisement.getTitle());
        advertisementEdit.setStartDate(advertisement.getStartDate().toString());
        advertisementEdit.setEndDate(advertisement.getEndDate().toString());
//        advertisementEdit.setAdCategory(advertisement.getCategory().getCategoryName());
        advertisementEdit.setAdContent(advertisement.getContent());
        advertisementEdit.setCreatedBy(advertisement.getAdmin().getEmail());
        advertisementEdit.setPcBannerUrl(advertisement.getPcBannerUrl());
        advertisementEdit.setMobileBannerUrl(advertisement.getMobileBannerUrl());
        advertisementEdit.setPcBannerLink(advertisement.getPcBannerLink());
        advertisementEdit.setMobileBannerLink(advertisement.getMobileBannerLink());

        return advertisementEdit;
    }

    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> advertisements = adminAdvertisementRepository.findAll();


        return adminAdvertisementRepository.findAll();
    }

    public Optional<Advertisement> getAdvertisementById(Long id) {
        return adminAdvertisementRepository.findById(id);
    }

    public Advertisement createAdvertisement(Advertisement advertisement) {

        return adminAdvertisementRepository.save(advertisement);
    }

    public Advertisement updateAdvertisement(Advertisement advertisement) {
        return adminAdvertisementRepository.save(advertisement);
    }


}
