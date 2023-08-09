package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.request.ItemSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Long findItemId(Long item_id);

    List<Item> find10ItemByCondition(String country,String city,
                                     String title,
                                     String content_1,
                                     String content_2);
    List<Long> findAllItemId();
    List<Item> find10BestItem();
    List<Item> find10CurrentItem();

    List<Item> find10CategoryBestItem(String category);

    List<Item> find10CategoryCurrentItem(String category);

    Page<Item> findCategoryBestItemPage(String category, Pageable pageable);
    Page<Item> findCategoryCurrentItemPage(String category, Pageable pageable);

    Page<Item> findItemByCondition(ItemSearchRequest.ItemConditionDto itemConditionDto, Pageable pageable);
    Page<Item> searchItemByWordAndSort(String[] contain_words,Pageable pageable);
}
