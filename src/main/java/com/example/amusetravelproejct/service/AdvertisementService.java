package com.example.amusetravelproejct.service;


import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.Advertisement;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.AdvertisementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class AdvertisementService {
    private final AdvertisementRepository AdvertisementRepository;
    private final AdminRepository adminRepository;
    private final AdminService adminService;

    public AdminPageResponse.advertisementRegister processAdvertisementRegister(AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto, UtilMethod utilMethod,Admin admin) {

        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(adminAdvertisementRegisterDto.getTitle());
        advertisement.setContent(adminAdvertisementRegisterDto.getAdContent());

        if(adminAdvertisementRegisterDto.getStartDate() != null){
            advertisement.setStartDate(Date.valueOf(adminAdvertisementRegisterDto.getStartDate()));
        }else{
            advertisement.setStartDate(null);
        }

        if(adminAdvertisementRegisterDto.getEndDate() != null){
            advertisement.setEndDate(Date.valueOf(adminAdvertisementRegisterDto.getEndDate()));
        }else{
            advertisement.setEndDate(null);
        }

        advertisement.setAdmin(admin);

        String category = "";

        if(adminAdvertisementRegisterDto.getAdCategory() == null || adminAdvertisementRegisterDto.getAdCategory().length == 0){
            advertisement.setCategory(null);
        }else{
            for (String s : adminAdvertisementRegisterDto.getAdCategory()) {
                category += s + ",";
            }
        }

        advertisement.setCategory(category);

        if(adminAdvertisementRegisterDto.getPcBannerBase64() != null){
            advertisement.setPcBannerUrl(utilMethod.getImgUrl(adminAdvertisementRegisterDto.getPcBannerBase64(), adminAdvertisementRegisterDto.getPcBannerFileName()));
        }else{
            advertisement.setPcBannerUrl(null);
        }

        if(adminAdvertisementRegisterDto.getMobileBannerBase64() != null){
            advertisement.setMobileBannerUrl(utilMethod.getImgUrl(adminAdvertisementRegisterDto.getMobileBannerBase64(), adminAdvertisementRegisterDto.getMobileBannerFileName()));
        }else{
            advertisement.setMobileBannerUrl(null);
        }

        advertisement.setPcBannerLink(adminAdvertisementRegisterDto.getPcBannerLink());
        advertisement.setMobileBannerLink(adminAdvertisementRegisterDto.getMobileBannerLink());
        AdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementRegister advertisementRegister = new AdminPageResponse.advertisementRegister();
        advertisementRegister.setId(advertisement.getId());
        advertisementRegister.setTitle(advertisement.getTitle());
        advertisementRegister.setStartDate(advertisement.getStartDate() == null ? null :  advertisement.getStartDate().toString());
        advertisementRegister.setEndDate(advertisement.getEndDate() == null ? null :  advertisement.getEndDate().toString());
        advertisementRegister.setAdCategory(advertisement.getCategory().split(","));
        advertisementRegister.setAdContent(advertisement.getContent());
        advertisementRegister.setCreatedBy(advertisement.getAdmin() == null ? null :  advertisement.getAdmin().getAdminId());
        advertisementRegister.setPcBannerUrl(advertisement.getPcBannerUrl());
        advertisementRegister.setMobileBannerUrl(advertisement.getMobileBannerUrl());
        advertisementRegister.setPcBannerLink(advertisement.getPcBannerLink());
        advertisementRegister.setMobileBannerLink(advertisement.getMobileBannerLink());

        return advertisementRegister;
    }


    public AdminPageResponse.advertisementEdit processAdvertisementEdit(AdminPageRequest.advertisementEdit advertisementEditDto, UtilMethod utilMethod) {
        Advertisement advertisement = AdvertisementRepository.findById(advertisementEditDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.ADVERTISEMENT_NOT_FOUND));
        advertisement.setTitle(advertisementEditDto.getTitle());

        if(advertisementEditDto.getStartDate() != null){
            advertisement.setStartDate(Date.valueOf(advertisementEditDto.getStartDate()));
        }

        if(advertisementEditDto.getEndDate() != null){
            advertisement.setEndDate(Date.valueOf(advertisementEditDto.getEndDate()));
        }

        if(advertisementEditDto.getAdContent() != null){
            advertisement.setContent(advertisementEditDto.getAdContent());
        }


        String category = "";

        if(advertisementEditDto.getAdCategory() != null ){
            for (String s : advertisementEditDto.getAdCategory()) {
                category += s + ",";
            }
            advertisement.setCategory(category);
        }



        //TODO
        // S3에 저장된 기존 배너 이미지 삭제
        if(advertisementEditDto.getPcBannerBase64() != null){
            advertisement.setPcBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getPcBannerBase64(), advertisementEditDto.getPcBannerFileName()));
        }else{
            advertisement.setPcBannerUrl(null);
        }

        if(advertisementEditDto.getMobileBannerBase64() != null){
            advertisement.setMobileBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getMobileBannerBase64(), advertisementEditDto.getMobileBannerFileName()));
        }else{
            advertisement.setMobileBannerUrl(null);
        }

        advertisement.setPcBannerLink(advertisementEditDto.getPcBannerLink());
        advertisement.setMobileBannerLink(advertisementEditDto.getMobileBannerLink());
        advertisement.setUpdateAdmin(adminService.getAdminByAdminId(advertisementEditDto.getUpdatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        AdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementEdit advertisementEdit = new AdminPageResponse.advertisementEdit();
        advertisementEdit.setId(advertisement.getId());
        advertisementEdit.setTitle(advertisement.getTitle());
        advertisementEdit.setStartDate(advertisement.getStartDate() == null ? null :  advertisement.getStartDate().toString());
        advertisementEdit.setEndDate(advertisement.getEndDate() == null ? null :  advertisement.getEndDate().toString());
        advertisementEdit.setAdCategory(advertisement.getCategory().split(","));
        advertisementEdit.setAdContent(advertisement.getContent());
        advertisementEdit.setCreatedAt(advertisement.getCreatedAt() == null ? null : advertisement.getCreatedAt().toString());
        advertisementEdit.setCreatedBy(advertisement.getAdmin() == null ? null : advertisement.getAdmin().getAdminId());
        advertisementEdit.setUpdatedAt(advertisement.getModifiedAt() == null ? null : advertisement.getModifiedAt().toString());
        advertisementEdit.setUpdatedBy(advertisement.getUpdateAdmin() == null ? null : advertisement.getUpdateAdmin().getAdminId());
        advertisementEdit.setPcBannerUrl(advertisement.getPcBannerUrl());
        advertisementEdit.setMobileBannerUrl(advertisement.getMobileBannerUrl());
        advertisementEdit.setPcBannerLink(advertisement.getPcBannerLink());
        advertisementEdit.setMobileBannerLink(advertisement.getMobileBannerLink());

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
            advertisementList.setCreatedBy(advertisement.getAdmin() == null ? null : advertisement.getAdmin().getAdminId());
            advertisementList.setUpdatedAt(advertisement.getUpdateAdmin() == null ? null : advertisement.getModifiedAt());
            advertisementList.setUpdatedBy(advertisement.getUpdateAdmin() == null ? "" : advertisement.getUpdateAdmin().getAdminId());
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
        advertisementEdit.setStartDate(advertisement.getStartDate() == null ? null : advertisement.getStartDate().toString());
        advertisementEdit.setEndDate(advertisement.getEndDate() == null ? null : advertisement.getEndDate().toString());
        advertisementEdit.setAdCategory(advertisement.getCategory().split(","));
        advertisementEdit.setAdContent(advertisement.getContent());
        advertisementEdit.setCreatedAt(advertisement.getCreatedAt() == null ? null : advertisement.getCreatedAt().toString());
        advertisementEdit.setCreatedBy(advertisement.getAdmin().getAdminId());
        advertisementEdit.setUpdatedAt(advertisement.getUpdateAdmin() != null ? advertisement.getModifiedAt().toString() : "");
        advertisementEdit.setUpdatedBy(advertisement.getUpdateAdmin() != null ? advertisement.getUpdateAdmin().getAdminId() : "");
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
