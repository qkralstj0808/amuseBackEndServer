package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.domain.ItemHashTag;
import com.example.amusetravelproejct.exception.ResourceNotFoundException;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;


//    public Optional<ItemHashTag> getCategoryByName(String name) {
//        return categoryRepository.findByCategoryName(name);
//    }

//    public ItemHashTag processCategoryRegister(String hashTag, Item item) {
//        ItemHashTag category = new ItemHashTag();
//
//        category.setItem(item);
//        category.setHashTag(hashTag);
//        return Optional.of(categoryRepository.save(category)).orElseThrow(
//                () -> new ResourceNotFoundException("해당 카테고리가 없습니다.")
//        );
//    }
//    public List<AdminPageResponse.category> processFindAllCategory() {
//
//
//        List<ItemHashTag> categories = categoryRepository.findAll();
//
//        List<AdminPageResponse.category> categoryList = new ArrayList<>();
//        for(int i = 0; i < categories.size(); i++){
//            ItemHashTag category = categories.get(i);
//            System.out.println(category);
//            categoryList.add(new AdminPageResponse.category(category.getId(),category.getCategoryName(),category.getCreatedAdDate(),category.getAdmin().getEmail(), (long) category.getItems().size()));
//        }
//        return categoryList;
//    }
//
//    public List<AdminPageResponse.categoryDetail> processCategoryItemList(ItemHashTag category, Long offset, Long limit) {
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

    public ItemHashTag findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당 카테고리가 없습니다.")
        );
    }






}
