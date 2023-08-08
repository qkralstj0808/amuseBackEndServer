package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    public final ItemHashTagRepository ItemhashTagRepository;

    public final ItemRepository itemRepository;
    public final CategoryRepository categoryRepository;

    public final PageComponentRepository pageComponentRepository;
    public final MainPageRepository mainPageRepository;

    public final TargetUserRepository targetUserRepository;


    public ResponseTemplate<MainPageResponse.getCategory> getCategory() {

        List<Category> categories = categoryRepository.findAllByDisableSortBySequence(false);

        return new ResponseTemplate(new MainPageResponse.getCategory(categories.stream().map(
                category -> new MainPageResponse.CategoryInfo(category.getId(),category.getSequence(),category.getCategory_name(),category.getImgUrl(),
                        category.getMainDescription(),category.getSubDescription())
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

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(),item.getItemCode(),item.getItemHashTags().stream().map(
                        itemHashTag -> new MainPageResponse.HashTag(itemHashTag.getHashTag())
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

                List<ItemHashTag> itemHashTag_list = item.getItemHashTags();

                // img가 하나라도 있다면
                if (item.getItemImg_list().size() != 0) {
                    itemImg = item.getItemImg_list().get(0).getImgUrl();
                }

                itemInfo.add(new MainPageResponse.ItemInfo(item.getId(), item.getItemCode(), item.getItemHashTags().stream().map(
                        itemHashTag -> new MainPageResponse.HashTag(itemHashTag.getHashTag())
                ).collect(Collectors.toList()),
                        itemImg, item.getTitle(), item.getCountry(), item.getCity(), item.getDuration(),
                        item.getLike_num(), item.getStartPrice()));
            }
        }

        return new ResponseTemplate<>(new MainPageResponse.getItem(itemInfo));
    }


    public ResponseTemplate<MainPageResponse.getListItem> getListItem() {

//        List<PageComponent> itemInListsByMainPageRequestListDtos = mainPageRepository.findItemInListsByMainPageRequestListDto();
//
//        MainPageResponse.getListItem getListItem = new MainPageResponse.getListItem(itemInListsByMainPageRequestListDtos.size(),itemInListsByMainPageRequestListDtos.stream().map(
//                itemInListsByMainPageRequestListDto -> new MainPageResponse.ListItem(
//                        itemInListsByMainPageRequestListDto.getTitle(),
//                        itemInListsByMainPageRequestListDto.getId(),
//                        itemInListsByMainPageRequestListDto.getMainPages().size(),
//                        itemInListsByMainPageRequestListDto.getMainPages().stream().map(
//                                mainPage -> new MainPageResponse.ItemInfo(
//                                        mainPage.getItem().getId(),
//                                        mainPage.getItem().getItemCode(),
//                                        mainPage.getItem().getItemHashTags().stream().map(
//                                                itemHashTag -> new MainPageResponse.HashTag(itemHashTag.getHashTag())
//                                        ).collect(Collectors.toList()),
//                                        mainPage.getItem().getItemImg_list().size() == 0 ? null : mainPage.getItem().getItemImg_list().get(0).getImgUrl(),
//                                        mainPage.getItem().getTitle(),
//                                        mainPage.getItem().getCountry(),
//                                        mainPage.getItem().getCity(),
//                                        mainPage.getItem().getDuration(),
//                                        mainPage.getItem().getLike_num(),
//                                        mainPage.getItem().getStartPrice()
//                                )
//                        ).collect(Collectors.toList()))).collect(Collectors.toList()));

//        return new ResponseTemplate(getListItem);
        return null;
    }

    public ResponseTemplate<MainPageResponse.getCategoryPage> getCategoryPage(Long category_id, Grade grade) {

        Category category = categoryRepository.findById(category_id).orElseThrow(
                () -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND)
        );


        List<Object> pageComponentInfoList = new ArrayList<>();

        // 한 카테고리에 들어가는 component 개수
        for (int i = 0; i < category.getCategoryPageComponents().size(); i++) {
            log.info("category.getCategoryPageComponents().size() : " + category.getCategoryPageComponents().size());
            CategoryPageComponent categoryPageComponent = category.getCategoryPageComponents().get(i);
            PageComponent pageComponent = categoryPageComponent.getPageComponent();

            // 각 component 별로

            // 타일
            if (pageComponent.getType().equals("타일")) {

                // 해당 카테고리에 속한 타일의 종류를 반환
                List<Long> tileIds = mainPageRepository.findTileIds(pageComponent.getId());
                log.info(String.valueOf(tileIds.size()));
                List<MainPageResponse.TileInfo> tileInfoList = new ArrayList<>();

                for (int k = 0; k < tileIds.size(); k++) {
                    log.info(String.valueOf(tileIds.get(k)));
                    List<MainPage> mainPage_by_tile_id = mainPageRepository.findMainPageByComponent_idAndTyle_idAndDisplayTrueAndGrade(pageComponent.getId(), tileIds.get(k),grade);
                    List<MainPageResponse.ItemInfo> itemInfoList = new ArrayList<MainPageResponse.ItemInfo>();

                    for (int q = 0; q < mainPage_by_tile_id.size(); q++) {
                        MainPage mainPage = mainPage_by_tile_id.get(q);
                        Item item = mainPage_by_tile_id.get(q).getItem();
                        MainPageResponse.ItemInfo itemInfo = new MainPageResponse.ItemInfo(
                                item.getId(),
                                item.getItemCode(),
                                item.getItemHashTags().stream().map(
                                        itemHashTag -> new MainPageResponse.HashTag(
                                                itemHashTag.getHashTag()
                                        )
                                ).collect(Collectors.toList()),
                                item.getItemImg_list().size() != 0 ? item.getItemImg_list().get(0).getImgUrl() : null,
                                item.getTitle(),
                                item.getCountry(),
                                item.getCity(),
                                item.getDuration(),
                                item.getLike_num(),
                                item.getStartPrice()
                        );
                        itemInfoList.add(itemInfo);
                    }

                    MainPageResponse.TileInfo tileInfo = new MainPageResponse.TileInfo(
                            mainPage_by_tile_id.get(k).getTile().getId(),
                            mainPage_by_tile_id.get(k).getTile().getTileName(),
                            mainPage_by_tile_id.get(k).getTile().getImgUrl(),
                            itemInfoList
                    );

                    tileInfoList.add(tileInfo);

                }

                MainPageResponse.PageTileInfo pageTileInfo = new MainPageResponse.PageTileInfo(
                        pageComponent.getId(),
                        pageComponent.getType(),
                        pageComponent.getTitle(),
                        tileIds.size(),
                        tileInfoList
                );
                pageComponentInfoList.add(pageTileInfo);

            // 리스트
            } else if (pageComponent.getType().equals("리스트")){
                List<MainPageResponse.ItemInfo> itemInfoList = new ArrayList<>();

                List<MainPage> mainPageList = mainPageRepository.findMainPageByPageComponentIdAndItemDisplayWithGrade(pageComponent.getId(), true,grade);
//                for (int k = 0; k < pageComponent.getMainPages().size(); k++) {
//                    Item item = pageComponent.getMainPages().get(k).getItem();
                for (int k = 0; k < mainPageList.size(); k++) {
                    Item item = mainPageList.get(k).getItem();

                    MainPageResponse.ItemInfo itemInfo = new MainPageResponse.ItemInfo(
                            item.getId(),
                            item.getItemCode(),
                            item.getItemHashTags().stream().map(
                                    itemHashTag -> new MainPageResponse.HashTag(
                                            itemHashTag.getHashTag()
                                    )
                            ).collect(Collectors.toList()),
                            item.getItemImg_list().size() != 0 ? item.getItemImg_list().get(0).getImgUrl() : null,
                            item.getTitle(),
                            item.getCountry(),
                            item.getCity(),
                            item.getDuration(),
                            item.getLike_num(),
                            item.getStartPrice()
                    );
                    itemInfoList.add(itemInfo);
                }
                MainPageResponse.PageListInfo pageListInfo = new MainPageResponse.PageListInfo(
                        pageComponent.getId(),
                        pageComponent.getType(),
                        pageComponent.getTitle(),
                        itemInfoList
                );
                pageComponentInfoList.add(pageListInfo);

            // 배너
            }else{
                List<MainPageResponse.ItemInfo> itemInfoList = new ArrayList<>();

                List<MainPage> mainPageList = mainPageRepository.findMainPageByPageComponentIdAndItemDisplayWithGrade(pageComponent.getId(), true,grade);
//                for (int k = 0; k < pageComponent.getMainPages().size(); k++) {
//                    Item item = pageComponent.getMainPages().get(k).getItem();
                for (int k = 0; k < mainPageList.size(); k++) {
                    Item item = mainPageList.get(k).getItem();
                    MainPageResponse.ItemInfo itemInfo = new MainPageResponse.ItemInfo(
                            item.getId(),
                            item.getItemCode(),
                            item.getItemHashTags().stream().map(
                                    itemHashTag -> new MainPageResponse.HashTag(
                                            itemHashTag.getHashTag()
                                    )
                            ).collect(Collectors.toList()),
                            item.getItemImg_list().size() != 0 ? item.getItemImg_list().get(0).getImgUrl() : null,
                            item.getTitle(),
                            item.getCountry(),
                            item.getCity(),
                            item.getDuration(),
                            item.getLike_num(),
                            item.getStartPrice()
                    );
                    itemInfoList.add(itemInfo);
                }
                MainPageResponse.PageBannerInfo pageComponentInfo = new MainPageResponse.PageBannerInfo(
                        pageComponent.getId(),
                        pageComponent.getType(),
                        pageComponent.getTitle(),
                        pageComponent.getPcBannerUrl(),
                        pageComponent.getPcBannerLink(),
                        pageComponent.getMobileBannerUrl(),
                        pageComponent.getMobileBannerLink(),
                        pageComponent.getContent(),
                        itemInfoList
                );
                pageComponentInfoList.add(pageComponentInfo);
            }
        }

        MainPageResponse.getCategoryPage getCategory = new MainPageResponse.getCategoryPage(
                category.getId(),
                category.getCategory_name(),
                category.getImgUrl(),
                category.getSequence(),
                category.getMainDescription(),
                category.getSubDescription(),
                category.getCategoryPageComponents().size(),
                pageComponentInfoList
        );


        return new ResponseTemplate(getCategory);
    }

    public ResponseTemplate<MainPageResponse.getItem> getMyItem(User findUser) {
        List<TargetUser> targetUsers = targetUserRepository.findByUserId(findUser.getId());

        return new ResponseTemplate(new MainPageResponse.getItem(targetUsers.stream().map(
                targetUser -> returnItemInfo(targetUser.getItem())
        ).collect(Collectors.toList())));
    }

    private MainPageResponse.ItemInfo returnItemInfo(Item item){
        return new MainPageResponse.ItemInfo(
                item.getId(),
                item.getItemCode(),
                item.getItemHashTags().stream().map(
                        itemHashTag -> new MainPageResponse.HashTag(
                                itemHashTag.getHashTag()
                        )
                ).collect(Collectors.toList()),
                item.getItemImg_list().size() != 0 ? item.getItemImg_list().get(0).getImgUrl() : null,
                item.getTitle(),
                item.getCountry(),
                item.getCity(),
                item.getDuration(),
                item.getLike_num(),
                item.getStartPrice()
        );
    }

}

