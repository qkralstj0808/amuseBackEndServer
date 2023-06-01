package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.MainPage;
import com.example.amusetravelproejct.domain.MainPageComponent;
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
public class MainPageComponentService {
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final MainPageComponentRepository mainPageComponentRepository;
    private final MainPageRepository mainPageRepository;
    private final TileRepository tileRepository;

    public ResponseTemplate<?> createMainPageComponent(AdminPageRequest.createMainPage createMainPageDto , UtilMethod utilMethod) {

        MainPageComponent mainPageComponent = createMainPageDto.getId() == null ? new MainPageComponent() : mainPageComponentRepository.findById(createMainPageDto.getId()).get();
        mainPageComponent.setAdmin(adminRepository.findByEmail(createMainPageDto.getCreateBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        mainPageComponent.setTitle(createMainPageDto.getTitle());
        mainPageComponent.setType(createMainPageDto.getType());

        List<MainPageComponent> mainPageComponents = mainPageComponentRepository.findAll();
        final int[] updateCheck = {0};
        mainPageComponents.forEach(mainPageComponentData -> {
            if (mainPageComponentData.getSequence() == createMainPageDto.getSequence()) {
                updateCheck[0] = 1;
                if (mainPageComponent.getId() == null){
                    mainPageComponent.setSequence(mainPageComponentData.getSequence());
                    mainPageComponentData.setSequence(0L);
                    mainPageComponentRepository.save(mainPageComponentData);
                } else{
                    Long tempSequence = mainPageComponent.getSequence();
                    mainPageComponent.setSequence(mainPageComponentData.getSequence());
                    mainPageComponentData.setSequence(tempSequence);
                    mainPageComponentRepository.save(mainPageComponentData);
                }
            }
        });

        List<MainPageComponent> mainPageComponentDates = mainPageComponentRepository.findBySequenceNotOrderBySequence(0L);

        mainPageComponentDates.forEach(data ->{
            log.info(String.valueOf(data.getSequence()));
        });

        if (updateCheck[0]==0) {
            if (createMainPageDto.getSequence() <= mainPageComponentDates.size() + 1){
                if (mainPageComponent.getId() == null){
                    mainPageComponent.setSequence(createMainPageDto.getSequence());
                } else{
//                    mainPageComponentDates.remove((int) (mainPageComponent.getSequence() - 1));
//                    mainPageComponent.setSequence(createMainPageDto.getSequence());
//
//                    for (int i = 0; i < mainPageComponentDates.size(); i++){
//                        mainPageComponents.get(i).setSequence((long) (i+1));
//                        mainPageComponentRepository.save(mainPageComponents.get(i));
//                    }
                    throw new CustomException(ErrorCode.SEQUENCE_ERROR_SET);
                }

            }else{
                 throw new CustomException(ErrorCode.SEQUENCE_ERROR_SET);
            }
        }

        if (createMainPageDto.getType().equals("리스트")) {
            mainPageComponentRepository.save(mainPageComponent);

            createMainPageDto.getItemCode().forEach(itemCode -> {
                MainPage mainPage = new MainPage();
                mainPage.setMainPageComponent(mainPageComponent);
                mainPage.setItem(itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND)));
                mainPageRepository.save(mainPage);
            });
        } else if (createMainPageDto.getType().equals("배너")) {
            mainPageComponent.setPcBannerUrl(utilMethod.getImgUrl(createMainPageDto.getPcBannerBase64(), createMainPageDto.getPcBannerFileName()));
            mainPageComponent.setPcBannerLink(createMainPageDto.getPcBannerLink());

            mainPageComponent.setMobileBannerUrl(utilMethod.getImgUrl(createMainPageDto.getMobileBannerBase64(), createMainPageDto.getMobileBannerFileName()));
            mainPageComponent.setMobileBannerLink(createMainPageDto.getMobileBannerLink());

            mainPageComponent.setContent(createMainPageDto.getContent());

            mainPageComponentRepository.save(mainPageComponent);

        } else if (createMainPageDto.getType().equals("타일")) {
           createMainPageDto.getTile().forEach(inputTile -> {
               Tile tile = new Tile();
               tile.setTileName(inputTile.getTileName());
               tile.setImgUrl(utilMethod.getImgUrl(inputTile.getTileBase64(), inputTile.getTileFileName()));
               tileRepository.save(tile);


               inputTile.getItemCode().forEach(itemCode -> {
                   MainPage mainPage = new MainPage();
                   mainPage.setTile(tile);
                   mainPage.setMainPageComponent(mainPageComponent);
                   mainPage.setItem(itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND)));
                   mainPageRepository.save(mainPage);
               });
           });

           mainPageComponentRepository.save(mainPageComponent);
        }


        return new ResponseTemplate<>("");
    }

    public ResponseTemplate<AdminPageRequest.createMainPage> processGetMainPageComponent(Long id) {
        MainPageComponent mainPageComponent = mainPageComponentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MAIN_PAGE_COMPONENT_NOT_FOUND));
        AdminPageRequest.createMainPage createMainPage = new AdminPageRequest.createMainPage();
        createMainPage.setId(mainPageComponent.getId());
        createMainPage.setTitle(mainPageComponent.getTitle());
        createMainPage.setType(mainPageComponent.getType());
        createMainPage.setSequence(mainPageComponent.getSequence());
        createMainPage.setCreateBy(mainPageComponent.getAdmin().getEmail());

        if (mainPageComponent.getType().equals("리스트")){
            List<String> itemCodeList = new ArrayList<>();
            mainPageRepository.findByMainPageComponent(mainPageComponent).forEach(mainPage -> {
                itemCodeList.add(mainPage.getItem().getItemCode());
            });

            createMainPage.setItemCode(itemCodeList);
        } else if (mainPageComponent.getType().equals("배너")){
            createMainPage.setPcBannerImgUrl(mainPageComponent.getPcBannerUrl());
            createMainPage.setPcBannerLink(mainPageComponent.getPcBannerLink());

            createMainPage.setMobileBannerImgUrl(mainPageComponent.getMobileBannerUrl());
            createMainPage.setMobileBannerLink(mainPageComponent.getMobileBannerLink());

            createMainPage.setContent(mainPageComponent.getContent());
        }
        else if (mainPageComponent.getType().equals("타일")){
            Set<Tile> tileList = new HashSet<>();

            mainPageRepository.findByMainPageComponent(mainPageComponent).forEach(mainPage -> {
                tileList.add(mainPage.getTile());
            });


            List<AdminPageRequest.getMainItem> getMainItems = new ArrayList<>();

            tileList.forEach(data ->{
                AdminPageRequest.getMainItem getMainItem = new AdminPageRequest.getMainItem();
                getMainItem.setTileName(data.getTileName());
                getMainItem.setTileImgUrl(data.getImgUrl());
                getMainItem.setTileImgUrl(data.getImgUrl());
                List<String> itemCodeList = new ArrayList<>();
                mainPageRepository.findByTile(data).forEach(mainPage -> {
                    itemCodeList.add(mainPage.getItem().getItemCode());
                });
                getMainItem.setItemCode(itemCodeList);
                getMainItems.add(getMainItem);
            });

            createMainPage.setTile(getMainItems);
        }


        return new ResponseTemplate<>(createMainPage);
    }

    public List<AdminPageResponse.getMainPageItem> processGetMainPageList() {
        List<MainPageComponent>  mainPageComponents = mainPageComponentRepository.findAll();
        List<AdminPageResponse.getMainPageItem> getMainPageLists = new ArrayList<>();

        mainPageComponents.forEach(mainPageComponent -> {
            AdminPageResponse.getMainPageItem getMainPageList = new AdminPageResponse.getMainPageItem();
            getMainPageList.setId(mainPageComponent.getId());
            getMainPageList.setTitle(mainPageComponent.getTitle());
            getMainPageList.setType(mainPageComponent.getType());
            getMainPageList.setSequence(mainPageComponent.getSequence());
            getMainPageList.setCreateAt(String.valueOf(mainPageComponent.getCreatedDate()));
            getMainPageList.setCreateBy(mainPageComponent.getAdmin().getEmail());
            getMainPageLists.add(getMainPageList);
        });

        return getMainPageLists;

    }

    @Transactional
    public void processDeleteMainPageComponent(Long id) {
        MainPageComponent mainPageComponent = mainPageComponentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.MAIN_PAGE_COMPONENT_NOT_FOUND));
        if (!mainPageComponent.getMainPages().isEmpty()){
            mainPageComponent.getMainPages().forEach(mainPage ->{
                mainPage.setItem(null);
                mainPage.setTile(null);
            });
        }

        mainPageComponentRepository.deleteById(id);
    }





}
