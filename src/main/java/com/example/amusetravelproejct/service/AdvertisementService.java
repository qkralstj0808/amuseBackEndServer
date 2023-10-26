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

    // advertisement 등록
    public AdminPageResponse.advertisementRegister processAdvertisementRegister(AdminPageRequest.advertisementRegister adminAdvertisementRegisterDto, UtilMethod utilMethod, Admin admin) {
        Advertisement advertisement = Advertisement.builder()
            .title(adminAdvertisementRegisterDto.getTitle())
            .content(adminAdvertisementRegisterDto.getAdContent())
            .startDate(adminAdvertisementRegisterDto.getStartDate() != null && !adminAdvertisementRegisterDto.getStartDate().equals("") ? Date.valueOf(adminAdvertisementRegisterDto.getStartDate()) : null)
            .endDate(adminAdvertisementRegisterDto.getEndDate() != null && !adminAdvertisementRegisterDto.getEndDate().equals("") ? Date.valueOf(adminAdvertisementRegisterDto.getEndDate()) : null)
            .admin(admin)
            .category(adminAdvertisementRegisterDto.getAdCategory() != null ? String.join(",", adminAdvertisementRegisterDto.getAdCategory()) : null)
            .pcBannerUrl(adminAdvertisementRegisterDto.getPcBannerBase64() != null ? utilMethod.getImgUrl(adminAdvertisementRegisterDto.getPcBannerBase64(), adminAdvertisementRegisterDto.getPcBannerFileName()) : null)
            .mobileBannerUrl(adminAdvertisementRegisterDto.getMobileBannerBase64() != null ? utilMethod.getImgUrl(adminAdvertisementRegisterDto.getMobileBannerBase64(), adminAdvertisementRegisterDto.getMobileBannerFileName()) : null)
            .pcBannerLink(adminAdvertisementRegisterDto.getPcBannerLink())
            .mobileBannerLink(adminAdvertisementRegisterDto.getMobileBannerLink())
            .build();

        AdvertisementRepository.save(advertisement);

        AdminPageResponse.advertisementRegister advertisementRegister = AdminPageResponse.advertisementRegister.builder()
            .id(advertisement.getId())
            .title(advertisement.getTitle())
            .startDate(advertisement.getStartDate() == null ? null : advertisement.getStartDate().toString())
            .endDate(advertisement.getEndDate() == null ? null : advertisement.getEndDate().toString())
            .adCategory(advertisement.getCategory() == null ? null : advertisement.getCategory().split(","))
            .adContent(advertisement.getContent())
            .createdBy(advertisement.getAdmin() == null ? null : advertisement.getAdmin().getAdminId())
            .pcBannerUrl(advertisement.getPcBannerUrl())
            .mobileBannerUrl(advertisement.getMobileBannerUrl())
            .pcBannerLink(advertisement.getPcBannerLink())
            .mobileBannerLink(advertisement.getMobileBannerLink())
            .build();

        return advertisementRegister;
    }



    // advertisement 수정
    public AdminPageResponse.advertisementEdit processAdvertisementEdit(AdminPageRequest.advertisementEdit advertisementEditDto, UtilMethod utilMethod) {
        Advertisement advertisement = AdvertisementRepository.findById(advertisementEditDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.ADVERTISEMENT_NOT_FOUND));

        // Update the properties using the builder pattern
        advertisement.builder()
            .title(advertisementEditDto.getTitle())
            .startDate(advertisementEditDto.getStartDate() != null && !advertisementEditDto.getStartDate().equals("") ? Date.valueOf(advertisementEditDto.getStartDate()) : null)
            .endDate(advertisementEditDto.getEndDate() != null && !advertisementEditDto.getEndDate().equals("") ? Date.valueOf(advertisementEditDto.getEndDate()) : null)
            .content(advertisementEditDto.getAdContent())
            .category(advertisementEditDto.getAdCategory() != null ? String.join(",", advertisementEditDto.getAdCategory()) : null)
            // Handle S3 image processing here
            .pcBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getPcBannerBase64(), advertisementEditDto.getPcBannerFileName()))
            .mobileBannerUrl(utilMethod.getImgUrl(advertisementEditDto.getMobileBannerBase64(), advertisementEditDto.getMobileBannerFileName()))
            .pcBannerLink(advertisementEditDto.getPcBannerLink())
            .mobileBannerLink(advertisementEditDto.getMobileBannerLink())
            .updateAdmin(adminService.getAdminByAdminId(advertisementEditDto.getUpdatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)))
            .build();

        AdvertisementRepository.save(advertisement);

        return AdminPageResponse.advertisementEdit.builder()
            .id(advertisement.getId())
            .title(advertisement.getTitle())
            .startDate(advertisement.getStartDate() == null ? null : advertisement.getStartDate().toString())
            .endDate(advertisement.getEndDate() == null ? null : advertisement.getEndDate().toString())
            .adCategory(advertisement.getCategory() == null ? null : advertisement.getCategory().split(","))
            .adContent(advertisement.getContent())
            .createdAt(advertisement.getCreatedAt() == null ? null : advertisement.getCreatedAt().toString())
            .createdBy(advertisement.getAdmin() == null ? null : advertisement.getAdmin().getAdminId())
            .updatedAt(advertisement.getModifiedAt() == null ? null : advertisement.getModifiedAt().toString())
            .updatedBy(advertisement.getUpdateAdmin() == null ? null : advertisement.getUpdateAdmin().getAdminId())
            .pcBannerUrl(advertisement.getPcBannerUrl())
            .mobileBannerUrl(advertisement.getMobileBannerUrl())
            .pcBannerLink(advertisement.getPcBannerLink())
            .mobileBannerLink(advertisement.getMobileBannerLink())
            .build();
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
            advertisementList.setAdCategory(advertisement.getCategory() == null ? null : advertisement.getCategory().split(","));
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
        advertisementEdit.setAdCategory(advertisement.getCategory() == null ? null : advertisement.getCategory().split(","));
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

//    public Advertisement createAdvertisement(Advertisement advertisement) {
//
//        return AdvertisementRepository.save(advertisement);
//    }
//
//    public Advertisement updateAdvertisement(Advertisement advertisement) {
//        return AdvertisementRepository.save(advertisement);
//    }
//

}
