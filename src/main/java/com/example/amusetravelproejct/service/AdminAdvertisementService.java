package com.example.amusetravelproejct.service;


import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.Advertisement;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdvertisementRepository;
import com.example.amusetravelproejct.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class AdminAdvertisementService {
    private final AdvertisementRepository AdvertisementRepository;

    public AdminPageResponse.advertisementRegister  processAdvertisementRegister(AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto , CategoryService categoryService, AdminService adminService, UtilMethod utilMethod) {

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(adminAdvertisementRegisterDto.getTitle());
        advertisement.setContent(adminAdvertisementRegisterDto.getAdContent());
        advertisement.setStartDate(Date.valueOf(adminAdvertisementRegisterDto.getStartDate()));
        advertisement.setEndDate(Date.valueOf(adminAdvertisementRegisterDto.getEndDate()));
        advertisement.setAdmin(adminService.getAdminByEmail(adminAdvertisementRegisterDto.getCreatedBy()).get());

        String category = "";
        for (String s : adminAdvertisementRegisterDto.getAdCategory()) {
            category += s + ",";
        }

        advertisement.setCategory(category);
        advertisement.setPcBannerUrl(utilMethod.getImgUrl(adminAdvertisementRegisterDto.getPcBannerBase64(), adminAdvertisementRegisterDto.getPcBannerFileName()));
        advertisement.setMobileBannerUrl(utilMethod.getImgUrl(adminAdvertisementRegisterDto.getMobileBannerBase64(), adminAdvertisementRegisterDto.getMobileBannerFileName()));
        advertisement.setPcBannerLink(adminAdvertisementRegisterDto.getPcBannerLink());
        advertisement.setMobileBannerLink(adminAdvertisementRegisterDto.getMobileBannerLink());
        AdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementRegister advertisementRegister = new AdminPageResponse.advertisementRegister();
        advertisementRegister.setId(advertisement.getId());
        advertisementRegister.setTitle(advertisement.getTitle());
        advertisementRegister.setStartDate(advertisement.getStartDate().toString());
        advertisementRegister.setEndDate(advertisement.getEndDate().toString());
        advertisementRegister.setAdCategory(advertisement.getCategory().split(","));
        advertisementRegister.setAdContent(advertisement.getContent());
        advertisementRegister.setCreatedBy(advertisement.getAdmin().getEmail());
        advertisementRegister.setPcBannerUrl(advertisement.getPcBannerUrl());
        advertisementRegister.setMobileBannerUrl(advertisement.getMobileBannerUrl());
        advertisementRegister.setPcBannerLink(advertisement.getPcBannerLink());
        advertisementRegister.setMobileBannerLink(advertisement.getMobileBannerLink());

        return advertisementRegister;
    }


    public AdminPageResponse.advertisementEdit processAdvertisementEdit(AdminPageRequest.advertisementEdit advertisementEditDto, CategoryService categoryService, AdminService adminService, UtilMethod utilMethod) {
        Advertisement advertisement = AdvertisementRepository.findById(advertisementEditDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Advertisement not Found"));

        advertisement.setTitle(advertisementEditDto.getTitle());
        advertisement.setStartDate(Date.valueOf(advertisementEditDto.getStartDate()));
        advertisement.setEndDate(Date.valueOf(advertisementEditDto.getEndDate()));
        advertisement.setContent(advertisementEditDto.getAdContent());

        String category = "";

        for (String s : advertisementEditDto.getAdCategory()) {
            category += s + ",";
        }

        advertisement.setCategory(category);


        //TODO
        // S3에 저장된 기존 배너 이미지 삭제
        advertisement.setPcBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getPcBannerBase64(), advertisementEditDto.getPcBannerFileName()));
        advertisement.setMobileBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getMobileBannerBase64(), advertisementEditDto.getMobileBannerFileName()));

        advertisement.setPcBannerLink(advertisementEditDto.getPcBannerLink());
        advertisement.setMobileBannerLink(advertisementEditDto.getMobileBannerLink());
        advertisement.setUpdateAdmin(adminService.getAdminByEmail(advertisementEditDto.getUpdatedBy()).get());
        AdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementEdit advertisementEdit = new AdminPageResponse.advertisementEdit();
        advertisementEdit.setId(advertisement.getId());
        advertisementEdit.setTitle(advertisement.getTitle());
        advertisementEdit.setStartDate(advertisement.getStartDate().toString());
        advertisementEdit.setEndDate(advertisement.getEndDate().toString());
        advertisementEdit.setAdCategory(advertisement.getCategory().split(","));
        advertisementEdit.setAdContent(advertisement.getContent());
        advertisementEdit.setCreatedAt(advertisement.getCreatedAt().toString());
        advertisementEdit.setCreatedBy(advertisement.getAdmin().getEmail());
        advertisementEdit.setUpdatedAt(advertisement.getUpdateAdmin() != null ? advertisement.getModifiedAt().toString() : "");
        advertisementEdit.setUpdatedBy(advertisement.getUpdateAdmin() != null ? advertisement.getUpdateAdmin().getEmail() : "");
        advertisementEdit.setPcBannerUrl(advertisement.getPcBannerUrl() != null ? advertisement.getPcBannerUrl() : "");
        advertisementEdit.setMobileBannerUrl(advertisement.getMobileBannerUrl() != null ? advertisement.getMobileBannerUrl() : "");
        advertisementEdit.setPcBannerLink(advertisement.getPcBannerLink() != null ? advertisement.getPcBannerLink() : "");
        advertisementEdit.setMobileBannerLink(advertisement.getMobileBannerLink() != null ? advertisement.getMobileBannerLink() : "");

        return advertisementEdit;
    }

    public List<AdminPageResponse.advertisementList> processGetAllAdvertisements(Long offset, int limit, int page) {

        int pageSize = limit;
        Long offsetCount = offset;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        List<Advertisement> advertisements = AdvertisementRepository.findAdsByOffsetAndLimitCount(offsetCount, pageable);

        List<AdminPageResponse.advertisementList> advertisementLists = new ArrayList<>();

        for (Advertisement advertisement : advertisements) {
            AdminPageResponse.advertisementList advertisementList = new AdminPageResponse.advertisementList();
            advertisementList.setId(advertisement.getId());
            advertisementList.setTitle(advertisement.getTitle());
            advertisementList.setStartDate(advertisement.getStartDate());
            advertisementList.setEndDate(advertisement.getEndDate());
            advertisementList.setAdCategory(advertisement.getCategory().split(","));
            advertisementList.setCreatedAt(advertisement.getCreatedAt());
            advertisementList.setCreatedBy(advertisement.getAdmin().getEmail());
            advertisementList.setUpdatedAt(advertisement.getUpdateAdmin() == null ? null : advertisement.getModifiedAt());
            advertisementList.setUpdatedBy(advertisement.getUpdateAdmin() == null ? "" : advertisement.getUpdateAdmin().getEmail());
//            advertisementList.setPcBannerUrl (advertisement.getPcBannerUrl());
//            advertisementList.setMobileBannerUrl(advertisement.getMobileBannerUrl());
//            advertisementList.setPcBannerLink(advertisement.getPcBannerLink());
//            advertisementList.setMobileBannerLink(advertisement.getMobileBannerLink());
            advertisementLists.add(advertisementList);
        }

        return advertisementLists;
    }
    public int getPageCount(int limit) {
        int pageSize = limit;
        int totalPage = (int) Math.ceil(AdvertisementRepository.count() / (double) pageSize);
        return totalPage;
    }

    public AdminPageResponse.advertisementEdit processGetAdvertisementDetail(Long id) {
        Advertisement advertisement = AdvertisementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Advertisement not Found"));

        AdminPageResponse.advertisementEdit advertisementEdit = new AdminPageResponse.advertisementEdit();
        advertisementEdit.setId(advertisement.getId());
        advertisementEdit.setTitle(advertisement.getTitle());
        advertisementEdit.setStartDate(advertisement.getStartDate().toString());
        advertisementEdit.setEndDate(advertisement.getEndDate().toString());
        advertisementEdit.setAdCategory(advertisement.getCategory().split(","));
        advertisementEdit.setAdContent(advertisement.getContent());
        advertisementEdit.setCreatedAt(advertisement.getCreatedAt().toString());
        advertisementEdit.setCreatedBy(advertisement.getAdmin().getEmail());
        advertisementEdit.setUpdatedAt(advertisement.getUpdateAdmin() != null ? advertisement.getModifiedAt().toString() : "");
        advertisementEdit.setUpdatedBy(advertisement.getUpdateAdmin() != null ? advertisement.getUpdateAdmin().getEmail() : "");
        advertisementEdit.setPcBannerUrl(advertisement.getPcBannerUrl() != null ? advertisement.getPcBannerUrl() : "");
        advertisementEdit.setMobileBannerUrl(advertisement.getMobileBannerUrl() != null ? advertisement.getMobileBannerUrl() : "");
        advertisementEdit.setPcBannerLink(advertisement.getPcBannerLink() != null ? advertisement.getPcBannerLink() : "");
        advertisementEdit.setMobileBannerLink(advertisement.getMobileBannerLink() != null ? advertisement.getMobileBannerLink() : "");

        return advertisementEdit;
    }

    public Advertisement createAdvertisement(Advertisement advertisement) {

        return AdvertisementRepository.save(advertisement);
    }

    public Advertisement updateAdvertisement(Advertisement advertisement) {
        return AdvertisementRepository.save(advertisement);
    }


}
