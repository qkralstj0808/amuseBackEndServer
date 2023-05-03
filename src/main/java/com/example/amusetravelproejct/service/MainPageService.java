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

    public ResponseTemplate<MainPageResponse.getBestItem> getBestItem() {
        List<Item> bestItems = itemRepository.find10BestItem();
        return returnBestItem(bestItems);
    }

    public ResponseTemplate<MainPageResponse.getCurrentItem> getCurrentItem() {
        List<Item> currentItems = itemRepository.find10CurrentItem();

        return returnCurrentItem(currentItems);

    }

    public ResponseTemplate<MainPageResponse.getBestItem> getCategoryBestItem(Long category_id) {
        List<Item> categoryBestItems = itemRepository.find10CategoryBestItem(category_id);
        return returnBestItem(categoryBestItems);
    }

    public ResponseTemplate<MainPageResponse.getCurrentItem> getCategoryCurrentItem(Long category_id) {
        List<Item> categoryCurrentItems = itemRepository.find10CategoryCurrentItem(category_id);
        return returnCurrentItem(categoryCurrentItems);
    }

    public ResponseTemplate<MainPageResponse.getItem> getCategoryAllItem(Long category_id) {
//        return returnItem(itemRepository.findCategoryAllItemPage(category_id));
        return null;
    }

    private ResponseTemplate<MainPageResponse.getBestItem> returnBestItem(List<Item> items) {

        return new ResponseTemplate(new MainPageResponse.getBestItem(items.stream().map(
                item -> new MainPageResponse.ItemInfo(
                        item.getItemCode(),item.getCategory().getCategoryName(),
                        item.getItemImg_list().get(0).getImgUrl(),item.getTitle(), item.getCountry(),
                        item.getCity(), item.getLike_num(), item.getStartingPrice())
        ).collect(Collectors.toList())));
    }

    private ResponseTemplate<MainPageResponse.getCurrentItem> returnCurrentItem(List<Item> items) {

        return new ResponseTemplate(new MainPageResponse.getCurrentItem(items.stream().map(
                item -> new MainPageResponse.ItemInfo(
                        item.getItemCode(),item.getCategory().getCategoryName(),
                        item.getItemImg_list().get(0).getImgUrl(),item.getTitle(), item.getCountry(),
                        item.getCity(), item.getLike_num(), item.getStartingPrice())
        ).collect(Collectors.toList())));
    }

    private ResponseTemplate<MainPageResponse.getItem> returnAllItem(List<Item> items) {

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

