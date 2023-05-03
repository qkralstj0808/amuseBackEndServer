package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.repository.CategoryRepository;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    public final CategoryRepository categoryRepository;

    public final ItemRepository itemRepository;


    public ResponseTemplate<MainPageResponse.getCategory> getCategory() {
        List<Category> categories = categoryRepository.findAll();

        return new ResponseTemplate(new MainPageResponse.getCategory(categories.stream().map(
                category -> new MainPageResponse.CategoryInfo(category.getId(),category.getCategoryName())
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<MainPageResponse.getItem> getBestItem() {
        List<Item> bestItems = itemRepository.find10BestItem();
        return returnItem(bestItems);
    }

    public ResponseTemplate<MainPageResponse.getItem> getCurrentItem() {
        List<Item> currentItems = itemRepository.find10CurrentItem();

//        System.out.println(currentItems.get(0));
        return returnItem(currentItems);

    }

    public ResponseTemplate<MainPageResponse.getItem> getCategoryBestItem(Long category_id) {
        List<Item> categoryBestItems = itemRepository.find10CategoryBestItem(category_id);
        return returnItem(categoryBestItems);
    }

    public ResponseTemplate<MainPageResponse.getItem> getCategoryCurrentItem(Long category_id) {
        List<Item> categoryCurrentItems = itemRepository.find10CategoryCurrentItem(category_id);
        return returnItem(categoryCurrentItems);
    }

    public ResponseTemplate<MainPageResponse.getItem> getCategoryAllItem(Long category_id) {
//        return returnItem(itemRepository.findCategoryAllItemPage(category_id));
        return null;
    }

    private ResponseTemplate<MainPageResponse.getItem> returnItem(List<Item> items) {

        return new ResponseTemplate(new MainPageResponse.getItem(items.stream().map(
                item -> new MainPageResponse.ItemInfo(
                        item.getItemCode(),item.getCategory().getCategoryName(),
                        item.getItemImg_list().get(0).getImgUrl(),item.getTitle(), item.getCountry(),
                        item.getCity(), item.getLike_num(), item.getStartingPrice())
        ).collect(Collectors.toList())));
    }

    private ResponseTemplate<MainPageResponse.getItem> returnItemDto(List<MainPageResponse.ItemInfo> items) {

        return new ResponseTemplate(new MainPageResponse.getItem(items));
    }


}

