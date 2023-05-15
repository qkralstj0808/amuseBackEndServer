//package com.example.amusetravelproejct.service;
//
//import com.example.amusetravelproejct.config.resTemplate.CustomException;
//import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
//import com.example.amusetravelproejct.domain.Admin;
//import com.example.amusetravelproejct.domain.Category;
//import com.example.amusetravelproejct.dto.request.AdminPageRequest;
//import com.example.amusetravelproejct.dto.response.AdminPageResponse;
//import com.example.amusetravelproejct.repository.CategoryRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//@Slf4j
//public class DisplayCategoryService {
//    private final CategoryRepository categoryRepository;
//    private final HashTagRepository hashTagRepository;
//    public List<AdminPageResponse.categoryDetail> processRegisterCategory(AdminPageRequest.categoryRegister categoryRegisterDto, AdminService adminService) {
//
//        List<AdminPageRequest.categoryDetail> categoryDetails = categoryRegisterDto.getHashTags();
//        Admin admin = adminService.getAdminByEmail(categoryRegisterDto.getManageBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));
//        List<AdminPageResponse.categoryDetail> categoryDetailList = new ArrayList<>();
//
//
//
//        categoryDetails.forEach(categoryDetail -> {
//            HashTag hashTag = hashTagRepository.findByHashTag(categoryDetail.getHashTag()).orElseThrow( () -> new CustomException(ErrorCode.HASH_TAG_NOT_FOUND));
//            Category category = categoryRepository.findByHashTag(hashTag).isEmpty() ? null : categoryRepository.findByHashTag(hashTag).orElseThrow(() -> new CustomException(ErrorCode.DISPLAY_CATEGORY_NOT_FOUND));
//            if (category == null){
//
//                category = new Category();
//                category.setAdmin(admin);
//                category.setHashTag(hashTag);
//                category.setSequence(categoryDetail.getSequence());
//                category = categoryRepository.save(category);
//                categoryDetailList.add(new AdminPageResponse.categoryDetail(category.getId(), hashTag.getHashTag(),
//                        category.getSequence(), category.getCreatedDate(), category.getAdmin().getEmail(),null,""));
//
//            }else{
//
//                category.setUpdateAdmin(admin);
//                category.setHashTag(hashTag);
//                category.setSequence(categoryDetail.getSequence());
//                category = categoryRepository.save(category);
//                categoryDetailList.add(new AdminPageResponse.categoryDetail(category.getId(), hashTag.getHashTag(),
//                        category.getSequence(), category.getCreatedDate(), category.getAdmin().getEmail(), category.getModifiedDate(), category.getUpdateAdmin().getEmail()));
//
//            }
//        });
//
//        return categoryDetailList;
//    }
//
//
//}
