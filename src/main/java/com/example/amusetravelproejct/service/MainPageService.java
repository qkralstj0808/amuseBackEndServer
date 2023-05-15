package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplateStatus;
import com.example.amusetravelproejct.domain.Category;
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

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    public final CategoryRepository categoryRepository;

    public final ItemRepository itemRepository;


    public ResponseTemplate<MainPageResponse.getCategory> getCategory() {
        List<Category> categories = categoryRepository.findAll();
        return null;
//        return new ResponseTemplate(new MainPageResponse.getCategory(categories.stream().map(
//                category -> new MainPageResponse.CategoryInfo(category.getId(),category.getCategoryName())
//        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<?> getBestItem() {
        List<Item> bestItems = itemRepository.find10BestItem();
        System.out.println("bestItems.size() : " + bestItems.size());
        return returnItem("best",bestItems);
    }

    public ResponseTemplate<?> getCurrentItem() {
        List<Item> currentItems = itemRepository.find10CurrentItem();

        return returnItem("current",currentItems);

    }

    public ResponseTemplate<?> getCategoryBestItem(Long category_id) {
        List<Item> categoryBestItems = itemRepository.find10CategoryBestItem(category_id);
        System.out.println(categoryBestItems.size());
        return returnItem("best",categoryBestItems);
    }

    public ResponseTemplate<?> getCategoryCurrentItem(Long category_id) {
        List<Item> categoryCurrentItems = itemRepository.find10CategoryCurrentItem(category_id);
        return returnItem("current",categoryCurrentItems);
    }

    public ResponseTemplate<?> getCategoryBestItemAllPage(Long category_id,int current_page,PageRequest pageRequest) {

        Page<Item> categoryBestItemPage = itemRepository.findCategoryBestItemPage(category_id, pageRequest);
        int total_page = categoryBestItemPage.getTotalPages();
        if(current_page > total_page-1){
            return new ResponseTemplate(ResponseTemplateStatus.OUT_BOUND_PAGE);
        }

        return returnItemPage("best",categoryBestItemPage,total_page,current_page);
    }

    public ResponseTemplate<?> getCategoryCurrentItemAllPage(Long category_id,int current_page,PageRequest pageRequest) {

        Page<Item> categoryCurrentItemPage = itemRepository.findCategoryCurrentItemPage(category_id, pageRequest);
        int total_page = categoryCurrentItemPage.getTotalPages();
        if(current_page > total_page-1){
            return new ResponseTemplate(ResponseTemplateStatus.OUT_BOUND_PAGE);
        }

        return returnItemPage("current",categoryCurrentItemPage,total_page,current_page);

    }

    private ResponseTemplate<?> returnItemPage(String type,Page<Item> categoryAllItemPage,int total_page,int current_page) {
        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();

        System.out.println("categoryAllItemPage.getContent().size() = " + categoryAllItemPage.getContent().size());
        System.out.println();
        if(categoryAllItemPage.getContent().size() != 0){
            for(int i = 0 ; i < categoryAllItemPage.getContent().size() ; i++){
                Item item = categoryAllItemPage.getContent().get(i);
                String categoryName = null;
                String itemImg = null;

//                // category가 있다면
//                if(item.getCategory() != null){
//                    categoryName = item.getCategory().getCategoryName();
//                }

                // img가 하나라도 있다면
                if(item.getItemImg_list().size() != 0){
                    itemImg = item.getItemImg_list().get(0).getImgUrl();
                }

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(),item.getItemCode(),categoryName,
                        itemImg,item.getTitle(),item.getCountry(),item.getCity(),item.getDuration(),
                        item.getLike_num(),item.getStartPrice()));

            }
        }

        if(type.equals("best")){
            return new ResponseTemplate(new MainPageResponse.getBestItemPage(itemInfo,total_page,current_page+1));
        }else if(type.equals("current")){
            return new ResponseTemplate(new MainPageResponse.getCurrentItemPage(itemInfo,total_page,current_page+1));
        }

        return null;
    }

    private ResponseTemplate<?> returnItem(String type,List<Item> items) {
        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();

        if(items.size() != 0){
            for(int i = 0 ; i < items.size() ; i++){
                Item item = items.get(i);
                String categoryName = null;
                String itemImg = null;

//                // category가 있다면
//                if(item.getCategory() != null){
//                    categoryName = item.getCategory().getCategoryName();
//                }

                // img가 하나라도 있다면
                if(item.getItemImg_list().size() != 0){
                    itemImg = item.getItemImg_list().get(0).getImgUrl();
                }

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(),item.getItemCode(),categoryName,
                        itemImg,item.getTitle(),item.getCountry(),item.getCity(),item.getDuration(),
                        item.getLike_num(),item.getStartPrice()));

            }
        }

        if(type.equals("best")){
            return new ResponseTemplate(new MainPageResponse.getBestItem(itemInfo));
        }else if(type.equals("current")){
            return new ResponseTemplate(new MainPageResponse.getCurrentItem(itemInfo));
        }
        return null;

    }


//    private ResponseTemplate<MainPageResponse.getCurrentItemPage> returnCurrentItemPage(Page<Item> categoryAllItemPage,int total_page,int current_page) {
//        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();
//
//        if(categoryAllItemPage.getSize() != 0){
//            for(int i = 0 ; i < categoryAllItemPage.getSize() ; i++){
//                Item item = categoryAllItemPage.getContent().get(i);
//                String categoryName = null;
//                String itemImg = null;
//
//                // category가 있다면
//                if(item.getCategory() != null){
//                    categoryName = item.getCategory().getCategoryName();
//                }
//
//                // img가 하나라도 있다면
//                if(item.getItemImg_list().size() != 0){
//                    itemImg = item.getItemImg_list().get(0).getImgUrl();
//                }
//
//                itemInfo.add(new MainPageResponse.ItemInfo(item.getItemCode(),categoryName,
//                        itemImg,item.getTitle(),item.getCountry(),item.getCity(),item.getDuration(),
//                        item.getLike_num(),item.getStartPrice()));
//
//            }
//        }
//
//
//    }
//
//    private ResponseTemplate<MainPageResponse.getCurrentItem> returnCurrentItem(List<Item> items) {
//        List<MainPageResponse.ItemInfo> itemInfo = new ArrayList<>();
//
//        if(items.size() != 0){
//            for(int i = 0 ; i < items.size() ; i++){
//                Item item = items.get(i);
//                String categoryName = null;
//                String itemImg = null;
//
//                // category가 있다면
//                if(item.getCategory() != null){
//                    categoryName = item.getCategory().getCategoryName();
//                }
//
//                // img가 하나라도 있다면
//                if(item.getItemImg_list().size() != 0){
//                    itemImg = item.getItemImg_list().get(0).getImgUrl();
//                }
//
//                itemInfo.add(new MainPageResponse.ItemInfo(item.getItemCode(),categoryName,
//                        itemImg,item.getTitle(),item.getCountry(),item.getCity(),item.getDuration(),
//                        item.getLike_num(),item.getStartPrice()));
//
//            }
//        }


//    }



}

