package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.MainPage;
import com.example.amusetravelproejct.domain.PageComponent;
import com.example.amusetravelproejct.domain.Tile;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.dto.response.AdminPageResponse;
import com.example.amusetravelproejct.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class PageComponentService {
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final PageComponentRepository pageComponentRepository;
    private final MainPageRepository mainPageRepository;
    private final TileRepository tileRepository;

    public AdminPageResponse.registerListComponent registerListComponent (AdminPageRequest.registerListComponent registerListComponent){
        PageComponent pageComponent = registerListComponent.getId() == null ? new PageComponent() : pageComponentRepository.findById(registerListComponent.getId()).orElseThrow(() -> new CustomException(ErrorCode.PAGE_COMPONENT_NOT_FOUND));
        pageComponent.setAdmin(adminRepository.findByAdminId(registerListComponent.getCreatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        pageComponent.setTitle(registerListComponent.getTitle());
        pageComponent.setType(registerListComponent.getType());

        if (registerListComponent.getId() != null){
            pageComponent.getMainPages().forEach(mainPage ->{
                mainPage.setPageComponent(null);
                mainPage.setItem(null);
            });
            pageComponent.getMainPages().clear();
            pageComponent.setUpdateAdmin(adminRepository.findByAdminId(registerListComponent.getUpdatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        }

        registerListComponent.getItemCode().forEach(itemCode -> {
            MainPage mainPage = new MainPage();
            mainPage.setPageComponent(pageComponent);
            mainPage.setItem(itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND)));

            mainPageRepository.save(mainPage);
        });
        pageComponentRepository.save(pageComponent);
        AdminPageResponse.registerListComponent registerListComponentDto = new AdminPageResponse.registerListComponent();
        registerListComponentDto.setId(pageComponent.getId());
        registerListComponentDto.setTitle(pageComponent.getTitle());
        registerListComponentDto.setType(pageComponent.getType());
        registerListComponentDto.setCreatedAt(pageComponent.getCreatedDate());
        registerListComponentDto.setCreatedBy(pageComponent.getAdmin().getAdminId());
        registerListComponentDto.setItemCode(registerListComponent.getItemCode());

        if (registerListComponent.getId() != null){
            registerListComponentDto.setUpdatedBy(pageComponent.getUpdateAdmin().getAdminId());
            registerListComponentDto.setUpdatedAt(pageComponent.getModifiedDate());
        }
        return registerListComponentDto;
    }

    public AdminPageResponse.registerBannerComponent registerBannerComponent (AdminPageRequest.registerBannerComponent registerBannerComponent,UtilMethod utilMethod){
        PageComponent pageComponent = registerBannerComponent.getId() == null ? new PageComponent() : pageComponentRepository.findById(registerBannerComponent.getId()).orElseThrow(() -> new CustomException(ErrorCode.PAGE_COMPONENT_NOT_FOUND));
        pageComponent.setAdmin(adminRepository.findByAdminId(registerBannerComponent.getCreatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        pageComponent.setTitle(registerBannerComponent.getTitle());
        pageComponent.setType(registerBannerComponent.getType());
        pageComponent.setContent(registerBannerComponent.getContent());

        if (registerBannerComponent.getId() != null){
            pageComponent.setUpdateAdmin(adminRepository.findByAdminId(registerBannerComponent.getUpdatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        }

        if (registerBannerComponent.getPcBannerBase64() != null){
            pageComponent.setPcBannerUrl(utilMethod.getImgUrl(registerBannerComponent.getPcBannerBase64(), registerBannerComponent.getPcBannerFileName()));
        }

        pageComponent.setPcBannerLink(registerBannerComponent.getPcBannerLink());

        if (registerBannerComponent.getMobileBannerBase64() != null){
            pageComponent.setMobileBannerUrl(utilMethod.getImgUrl(registerBannerComponent.getMobileBannerBase64(), registerBannerComponent.getMobileBannerFileName()));
        }
        pageComponent.setMobileBannerLink(registerBannerComponent.getMobileBannerLink());
        pageComponentRepository.save(pageComponent);

        AdminPageResponse.registerBannerComponent registerBannerComponentDto = new AdminPageResponse.registerBannerComponent();
        registerBannerComponentDto.setId(pageComponent.getId());
        registerBannerComponentDto.setTitle(pageComponent.getTitle());
        registerBannerComponentDto.setType(pageComponent.getType());
        registerBannerComponentDto.setCreatedAt(pageComponent.getCreatedDate());
        registerBannerComponentDto.setCreatedBy(pageComponent.getAdmin().getAdminId());
        registerBannerComponentDto.setPcBannerImgUrl(pageComponent.getPcBannerUrl());
        registerBannerComponentDto.setPcBannerLink(pageComponent.getPcBannerLink());
        registerBannerComponentDto.setMobileBannerImgUrl(pageComponent.getMobileBannerUrl());
        registerBannerComponentDto.setMobileBannerLink(pageComponent.getMobileBannerLink());
        registerBannerComponentDto.setContent(pageComponent.getContent());

        if (registerBannerComponent.getId() != null){
            registerBannerComponentDto.setUpdatedBy(pageComponent.getUpdateAdmin().getAdminId());
            registerBannerComponentDto.setUpdatedAt(pageComponent.getModifiedDate());
        }
        return registerBannerComponentDto;
    }

    public AdminPageResponse.registerTileComponent registerTileComponent (AdminPageRequest.registerTileComponent registerTileComponent,UtilMethod utilMethod){
        PageComponent pageComponent = registerTileComponent.getId() == null ? new PageComponent() : pageComponentRepository.findById(registerTileComponent.getId()).orElseThrow(() -> new CustomException(ErrorCode.PAGE_COMPONENT_NOT_FOUND));
        List<AdminPageResponse.getMainItem> tileRespDto = new ArrayList<>();
        pageComponent.setAdmin(adminRepository.findByAdminId(registerTileComponent.getCreatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        pageComponent.setTitle(registerTileComponent.getTitle());
        pageComponent.setType(registerTileComponent.getType());
        pageComponentRepository.save(pageComponent);

        if (registerTileComponent.getId() != null){
            pageComponent.getMainPages().forEach(mainPage ->{
                mainPage.setPageComponent(null);
                mainPage.setItem(null);
                mainPage.setTile(null);
            });
            pageComponent.getMainPages().clear();
            pageComponent.setUpdateAdmin(adminRepository.findByAdminId(registerTileComponent.getUpdatedBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        }

        registerTileComponent.getTile().forEach(tileDto -> {
            Tile tile = new Tile();
            AdminPageResponse.getMainItem tileResp = new AdminPageResponse.getMainItem();

            tile.setTileName(tileDto.getTileName());
            if (tileDto.getTileBase64() != null){
                tile.setImgUrl(utilMethod.getImgUrl(tileDto.getTileBase64(), tileDto.getTileFileName()));
            } else{
                tile.setImgUrl(tileDto.getTileImgUrl());
            }
           tileResp.setTileImgUrl(tile.getImgUrl());
           tileResp.setTileName(tile.getTileName());
           tileRepository.save(tile);

            tileDto.getItemCode().forEach(itemCode -> {
                   MainPage mainPage = new MainPage();
                   mainPage.setTile(tile);
                   mainPage.setPageComponent(pageComponent);
                   mainPage.setItem(itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND)));

                   mainPageRepository.save(mainPage);
            });

            tileResp.setItemCode(tileDto.getItemCode());
            tileRespDto.add(tileResp);
        });

        AdminPageResponse.registerTileComponent registerTileComponentDto = new AdminPageResponse.registerTileComponent();

        registerTileComponentDto.setId(pageComponent.getId());
        registerTileComponentDto.setTitle(pageComponent.getTitle());
        registerTileComponentDto.setType(pageComponent.getType());
        registerTileComponentDto.setCreatedBy(pageComponent.getAdmin().getAdminId());
        registerTileComponentDto.setTile(tileRespDto);

        if (registerTileComponent.getId() != null){
            registerTileComponentDto.setUpdatedBy(pageComponent.getUpdateAdmin().getAdminId());
            registerTileComponentDto.setUpdatedAt(pageComponent.getModifiedDate());
        }

        return registerTileComponentDto;
    }


    public List<AdminPageResponse.getMainComponent> getComponentList () {
        List<PageComponent> mainPageComponents = pageComponentRepository.findAll();
        List<AdminPageResponse.getMainComponent> getMainPageLists = new ArrayList<>();

        mainPageComponents.forEach(mainPageComponent -> {
            AdminPageResponse.getMainComponent getMainPageList = new AdminPageResponse.getMainComponent();
            getMainPageList.setId(mainPageComponent.getId());
            getMainPageList.setTitle(mainPageComponent.getTitle());
            getMainPageList.setType(mainPageComponent.getType());
            getMainPageList.setCreateAt(mainPageComponent.getCreatedDate());
            getMainPageList.setCreateBy(mainPageComponent.getAdmin().getAdminId());
            getMainPageList.setUpdateAt(mainPageComponent.getUpdateAdmin() == null ? null : mainPageComponent.getModifiedDate());
            getMainPageList.setUpdateBy(mainPageComponent.getUpdateAdmin() == null ? null : mainPageComponent.getUpdateAdmin().getAdminId());
            getMainPageLists.add(getMainPageList);
        });

        return getMainPageLists;
    }
    @Transactional
    public void processDeleteMainPageComponent(Long id) {
        PageComponent mainPageComponent = pageComponentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MAIN_PAGE_COMPONENT_NOT_FOUND));
        if (!mainPageComponent.getMainPages().isEmpty()){
            mainPageComponent.getMainPages().forEach(mainPage ->{
                mainPage.setItem(null);
                mainPage.setTile(null);
            });
        }

        pageComponentRepository.deleteById(id);
    }


    public Object getComponentDetail(Long id) {
        PageComponent pageComponent = pageComponentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MAIN_PAGE_COMPONENT_NOT_FOUND));

        if (pageComponent.getType().equals("리스트")){
            AdminPageResponse.getListComponent getListComponentDto = new AdminPageResponse.getListComponent();
            getListComponentDto.setId(pageComponent.getId());
            getListComponentDto.setTitle(pageComponent.getTitle());
            getListComponentDto.setType(pageComponent.getType());
            getListComponentDto.setCreatedAt(pageComponent.getCreatedDate());
            getListComponentDto.setCreatedBy(pageComponent.getAdmin().getAdminId());
            getListComponentDto.setUpdatedAt(pageComponent.getUpdateAdmin() == null ? null : pageComponent.getModifiedDate());
            getListComponentDto.setUpdatedBy(pageComponent.getUpdateAdmin() == null ? null : pageComponent.getUpdateAdmin().getAdminId());
            List<AdminPageResponse.getMainPageItem> mainPageItemDto = new ArrayList<>();
            pageComponent.getMainPages().forEach(mainPage -> {
                AdminPageResponse.getMainPageItem mainPageItem = new AdminPageResponse.getMainPageItem();
                mainPageItem.setItem_db_id(mainPage.getItem().getId());
                mainPageItem.setProduct_code(mainPage.getItem().getItemCode());
                mainPageItem.setStartPrice(mainPage.getItem().getStartPrice());
                mainPageItem.setTitle(mainPage.getItem().getTitle());

                List<String> category = new ArrayList<>();
                mainPage.getItem().getItemHashTags().forEach(itemHashTag -> {
                    category.add(itemHashTag.getHashTag());
                });
                mainPageItem.setCategory(category);
                mainPageItem.setCreateAt(mainPage.getItem().getCreatedDate());
                mainPageItem.setCreateBy(mainPage.getItem().getAdmin().getAdminId());
                mainPageItem.setUpdateAt(mainPage.getItem().getUpdateAdmin() == null ? null : mainPage.getItem().getModifiedDate());
                mainPageItem.setUpdateBy(mainPage.getItem().getUpdateAdmin() == null ? null : mainPage.getItem().getUpdateAdmin().getAdminId());
                mainPageItemDto.add(mainPageItem);
            });
            getListComponentDto.setProductList(mainPageItemDto);
            return getListComponentDto;
        } else if(pageComponent.getType().equals("배너")){
            AdminPageResponse.getBannerComponent getBannerComponentDto = new AdminPageResponse.getBannerComponent();
            getBannerComponentDto.setId(pageComponent.getId());
            getBannerComponentDto.setTitle(pageComponent.getTitle());
            getBannerComponentDto.setType(pageComponent.getType());
            getBannerComponentDto.setCreatedAt(pageComponent.getCreatedDate());
            getBannerComponentDto.setCreatedBy(pageComponent.getAdmin().getAdminId());
            getBannerComponentDto.setUpdatedAt(pageComponent.getUpdateAdmin() == null ? null : pageComponent.getModifiedDate());
            getBannerComponentDto.setUpdatedBy(pageComponent.getUpdateAdmin() == null ? null : pageComponent.getUpdateAdmin().getAdminId());
            getBannerComponentDto.setPcBannerImgUrl(pageComponent.getPcBannerUrl());
            getBannerComponentDto.setPcBannerLink(pageComponent.getPcBannerLink());
            getBannerComponentDto.setMobileBannerImgUrl(pageComponent.getMobileBannerUrl());
            getBannerComponentDto.setMobileBannerLink(pageComponent.getMobileBannerLink());
            getBannerComponentDto.setContent(pageComponent.getContent());
            return getBannerComponentDto;
        } else{
            AdminPageResponse.getTileComponent getTileComponentDto = new AdminPageResponse.getTileComponent();
            getTileComponentDto.setId(pageComponent.getId());
            getTileComponentDto.setTitle(pageComponent.getTitle());
            getTileComponentDto.setType(pageComponent.getType());
            getTileComponentDto.setCreatedAt(pageComponent.getCreatedDate());
            getTileComponentDto.setCreatedBy(pageComponent.getAdmin().getAdminId());
            getTileComponentDto.setUpdatedAt(pageComponent.getUpdateAdmin() == null ? null : pageComponent.getModifiedDate());
            getTileComponentDto.setUpdatedBy(pageComponent.getUpdateAdmin() == null ? null : pageComponent.getUpdateAdmin().getAdminId());
            List<AdminPageResponse.getDetailItem> tileDto = new ArrayList<>();
            Set<Tile> tile = new HashSet<>();
            pageComponent.getMainPages().forEach(mainPage -> {
                tile.add(mainPage.getTile());
            });

            tile.forEach(mainPage ->{
                AdminPageResponse.getDetailItem detailItem = new AdminPageResponse.getDetailItem();
                detailItem.setTitle(mainPage.getTileName());
                List<AdminPageResponse.getMainPageItem> mainPageItems = new ArrayList<>();

                mainPageRepository.findByTile(mainPage).forEach(mainPageItem ->{
                    AdminPageResponse.getMainPageItem tileItems = new AdminPageResponse.getMainPageItem();
                    tileItems.setItem_db_id(mainPageItem.getItem().getId());
                    tileItems.setProduct_code(mainPageItem.getItem().getItemCode());
                    tileItems.setStartPrice(mainPageItem.getItem().getStartPrice());
                    tileItems.setTitle(mainPageItem.getItem().getTitle());
                    List<String> category = new ArrayList<>();
                    mainPageItem.getItem().getItemHashTags().forEach(itemHashTag -> {
                        category.add(itemHashTag.getHashTag());
                    });
                    tileItems.setCategory(category);
                    tileItems.setCreateAt(mainPageItem.getItem().getCreatedDate());
                    tileItems.setCreateBy(mainPageItem.getItem().getAdmin().getAdminId());
                    tileItems.setUpdateAt(mainPageItem.getItem().getUpdateAdmin() == null ? null : mainPageItem.getItem().getModifiedDate());
                    tileItems.setUpdateBy(mainPageItem.getItem().getUpdateAdmin() == null ? null : mainPageItem.getItem().getUpdateAdmin().getAdminId());
                    mainPageItems.add(tileItems);
                });
                detailItem.setProductList(mainPageItems);
                detailItem.setTileImgUrl(mainPage.getImgUrl());

                tileDto.add(detailItem);
            });
            getTileComponentDto.setTile(tileDto);
            return getTileComponentDto;
        }
    }
}
