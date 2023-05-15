package com.example.amusetravelproejct.service;
import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public AdminPageResponse.categoryRegister processRegisterCategory(AdminPageRequest.categoryRegister categoryRegisterDto, AdminService adminService, UtilMethod utilMethod) {



        Category category = new Category();
        Admin admin = adminService.getAdminByEmail(categoryRegisterDto.getCreatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));


        category.setCategory_name(categoryRegisterDto.getHashTag());
        category.setSequence(categoryRepository.count());
        category.setAdmin(admin);
        category.setMainDescription(categoryRegisterDto.getMainDescription());
        category.setSubDescription(categoryRegisterDto.getSubDescription());
        category.setImgUrl(utilMethod.getImgUrl(categoryRegisterDto.getBase64Date(), categoryRegisterDto.getFileName()));
        category = categoryRepository.save(category);


        return new AdminPageResponse.categoryRegister(category.getId(),category.getCategory_name(),category.getImgUrl(),category.getSequence(),category.getMainDescription(),category.getSubDescription(),category.getCreatedDate(),category.getAdmin().getEmail());
    }


    public AdminPageResponse.categoryEdit processEditCategory(AdminPageRequest.categoryEdit categoryEditDto,AdminService adminService, UtilMethod utilMethod){

        Category category = categoryRepository.findById(categoryEditDto.getId()).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setCategory_name(categoryEditDto.getHashTag());
        Admin admin = adminService.getAdminByEmail(categoryEditDto.getUpdatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND));


        if (categoryEditDto.getFileName() != ""){
            category.setImgUrl(utilMethod.getImgUrl(categoryEditDto.getBase64Date(), categoryEditDto.getFileName()));
        }

        category.setMainDescription(categoryEditDto.getMainDescription());
        category.setSubDescription(categoryEditDto.getSubDescription());
        category.setUpdateAdmin(admin);
        categoryRepository.save(category);
        return new AdminPageResponse.categoryEdit(category.getId(),category.getCategory_name(),category.getImgUrl(),category.getSequence(),category.getMainDescription(),category.getSubDescription(),category.getCreatedDate(),category.getAdmin().getEmail(),category.getModifiedDate(),category.getUpdateAdmin().getEmail());
    }
}

