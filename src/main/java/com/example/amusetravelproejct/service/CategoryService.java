package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;


//    public Optional<Category> getCategoryByName(String name) {
//        return categoryRepository.findByCategoryName(name);
//    }

    public Category processCategoryRegister(AdminPageRequest.categoryRegister  categoryRegisterDto) {
        Category category = new Category();
//        category.setCategoryName(categoryRegisterDto.getCategoryName());
//        category.setAdmin(adminRepository.findByEmail(categoryRegisterDto.getCreatedBy()).orElseThrow(
//                () -> new ResourceNotFoundException("해당 관리자가 없습니다.")
//        ));

        return Optional.of(categoryRepository.save(category)).orElseThrow(
                () -> new ResourceNotFoundException("해당 카테고리가 없습니다.")
        );
    }
//    public List<AdminPageResponse.category> processFindAllCategory() {
//
//
//        List<Category> categories = categoryRepository.findAll();
//
//        List<AdminPageResponse.category> categoryList = new ArrayList<>();
//        for(int i = 0; i < categories.size(); i++){
//            Category category = categories.get(i);
//            System.out.println(category);
//            categoryList.add(new AdminPageResponse.category(category.getId(),category.getCategoryName(),category.getCreatedAdDate(),category.getAdmin().getEmail(), (long) category.getItems().size()));
//        }
//        return categoryList;
//    }
//
//    public List<AdminPageResponse.categoryDetail> processCategoryItemList(Category category, Long offset, Long limit) {
//
//        int count = Math.toIntExact(limit > category.getItems().size() ? category.getItems().size() : limit);
//
//
//        List<AdminPageResponse.categoryDetail> categoryDetails = new ArrayList<>();
//
//
//        if(category.getItems().isEmpty()) {
//            return categoryDetails;
//        }
//        if(offset > count){
//            return categoryDetails;
//        }
//
//        List<Item> items = category.getItems().subList(0, count);
//        for(int i = 0; i < items.size(); i++){
//            Item item = items.get(i);
//            AdminPageResponse.categoryDetail categoryDetail = new AdminPageResponse.categoryDetail();
//            categoryDetail.setId(item.getId());
//            categoryDetail.setCode(item.getItemCode());
//            categoryDetail.setTitle(item.getTitle());
//            categoryDetail.setCreatedAt(item.getCreatedDate());
//            categoryDetail.setCreatedBy(item.getAdmin().getEmail());
//            if(item.getUpdateAdmin() == null) {
//                categoryDetail.setUpdatedAt(null);
//            } else{
//                categoryDetail.setUpdatedAt(item.getModifiedDate());
//            }
//            categoryDetails.add(categoryDetail);
//        }
//
//
//        return categoryDetails;
//    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당 카테고리가 없습니다.")
        );
    }






}
