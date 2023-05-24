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

import java.util.ArrayList;
import java.util.List;

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

        MainPageComponent mainPageComponent = new MainPageComponent();
        mainPageComponent.setAdmin(adminRepository.findByEmail(createMainPageDto.getCreateBy()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        mainPageComponent.setTitle(createMainPageDto.getTitle());
        mainPageComponent.setType(createMainPageDto.getType());
        mainPageComponent.setSequence(createMainPageDto.getSequence());

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
        createMainPage.setTitle(mainPageComponent.getTitle());
        createMainPage.setType(mainPageComponent.getType());
        createMainPage.setSequence(mainPageComponent.getSequence());

        if (mainPageComponent.getType().equals("리스트")){
            List<String> itemCodeList = new ArrayList<>();
            mainPageRepository.findByMainPageComponent(mainPageComponent).forEach(mainPage -> {
                itemCodeList.add(mainPage.getItem().getItemCode());
            });

//            createMainPage.setItemCode();
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
}
