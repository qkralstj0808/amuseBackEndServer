package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplateStatus;
import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.ItemHashTag;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.repository.CategoryRepository;
import com.example.amusetravelproejct.repository.ItemHashTagRepository;
import com.example.amusetravelproejct.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    public final ItemHashTagRepository ItemhashTagRepository;

    public final ItemRepository itemRepository;
    public final CategoryRepository categoryRepository;


    public ResponseTemplate<MainPageResponse.getCategory> getCategory() {

        List<Category> categories = categoryRepository.findAll();

        return new ResponseTemplate(new MainPageResponse.getCategory(categories.stream().map(
                category -> new MainPageResponse.CategoryInfo(category.getId(),category.getCategory_name())
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<?> getBestItem() {
        List<Item> bestItems = itemRepository.find10BestItem();
        return returnItem(bestItems);
    }

    public ResponseTemplate<?> getCurrentItem() {
        List<Item> currentItems = itemRepository.find10CurrentItem();

        return returnItem(currentItems);

    }

    public ResponseTemplate<?> getCategoryBestItem(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        List<Item> categoryBestItems = itemRepository.find10CategoryBestItem(category.getCategory_name());
        return returnItem(categoryBestItems);
    }

    public ResponseTemplate<?> getCategoryCurrentItem(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        List<Item> categoryCurrentItems = itemRepository.find10CategoryCurrentItem(category.getCategory_name());
        return returnItem(categoryCurrentItems);
    }

    public ResponseTemplate<?> getCategoryBestItemAllPage(Long category_id,int current_page,PageRequest pageRequest) {
        Category category = categoryRepository.findById(category_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        Page<Item> categoryBestItemPage = itemRepository.findCategoryBestItemPage(category.getCategory_name(), pageRequest);
        int total_page = categoryBestItemPage.getTotalPages();

        if(current_page == 1 && categoryBestItemPage.getContent().size() < 1){
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND_IN_PAGE);
        }

        if(current_page-1 > total_page-1){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        return returnItemPage(categoryBestItemPage,total_page,current_page);
    }

    public ResponseTemplate<?> getCategoryCurrentItemAllPage(Long category_id,int current_page,PageRequest pageRequest) {
        Category category = categoryRepository.findById(category_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );

        Page<Item> categoryCurrentItemPage = itemRepository.findCategoryCurrentItemPage(category.getCategory_name(), pageRequest);
        int total_page = categoryCurrentItemPage.getTotalPages();

        if(current_page == 1 && categoryCurrentItemPage.getContent().size() < 1){
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND_IN_PAGE);
        }

        if(current_page-1 > total_page-1){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        return returnItemPage(categoryCurrentItemPage,total_page,current_page);

    }

    private ResponseTemplate<?> returnItemPage(Page<Item> categoryAllItemPage, int total_page, int current_page) {
        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();

        System.out.println("categoryAllItemPage.getContent().size() = " + categoryAllItemPage.getContent().size());
        System.out.println();
        if(categoryAllItemPage.getContent().size() != 0){
            for(int i = 0 ; i < categoryAllItemPage.getContent().size() ; i++){
                Item item = categoryAllItemPage.getContent().get(i);
                String categoryName = null;
                String itemImg = null;

                // img가 하나라도 있다면
                if(item.getItemImg_list().size() != 0){
                    itemImg = item.getItemImg_list().get(0).getImgUrl();
                }

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(),item.getItemCode(),item.getItemHashTag_list().stream().map(
                        itemHashTag -> new MainPageResponse.HashTag(itemHashTag.getHash_tag())
                ).collect(Collectors.toList()),
                        itemImg,item.getTitle(),item.getCountry(),item.getCity(),item.getDuration(),
                        item.getLike_num(),item.getStartPrice()));

            }
        }
        return new ResponseTemplate<>(new MainPageResponse.getItemPage(itemInfo,total_page,current_page));
    }

    private ResponseTemplate<?> returnItem(List<Item> items) {
        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();

        if(items.size() != 0){
            for (Item item : items) {
                String itemImg = null;

                List<ItemHashTag> itemHashTag_list = item.getItemHashTag_list();

                // img가 하나라도 있다면
                if (item.getItemImg_list().size() != 0) {
                    itemImg = item.getItemImg_list().get(0).getImgUrl();
                }

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(), item.getItemCode(), item.getItemHashTag_list().stream().map(
                        itemHashTag -> new MainPageResponse.HashTag(itemHashTag.getHash_tag())
                ).collect(Collectors.toList()),
                        itemImg, item.getTitle(), item.getCountry(), item.getCity(), item.getDuration(),
                        item.getLike_num(), item.getStartPrice()));
            }
        }

        return new ResponseTemplate<>(new MainPageResponse.getItem(itemInfo));
    }



}

