package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.MainPage;
import com.example.amusetravelproejct.domain.MainPageComponent;
import com.example.amusetravelproejct.dto.request.AdminPageRequest;
import com.example.amusetravelproejct.repository.AdminRepository;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.repository.MainPageComponentRepository;
import com.example.amusetravelproejct.repository.MainPageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class MainPageComponentService {
    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final MainPageComponentRepository mainPageComponentRepository;
    private final MainPageRepository mainPageRepository;

    public ResponseTemplate<?> createMainPageComponent(AdminPageRequest.createMainPage createMainPageDto){

        MainPageComponent mainPageComponent = new MainPageComponent();
        mainPageComponent.setTitle(createMainPageDto.getTitle());
        mainPageComponent.setAdmin(adminRepository.findByEmail(createMainPageDto.getCreateAt()).orElseThrow(() -> new CustomException(ErrorCode.ADMIN_NOT_FOUND)));
        mainPageComponent.setType(createMainPageDto.getType());
        mainPageComponent.setSequence(createMainPageDto.getSequence());
        mainPageComponentRepository.save(mainPageComponent);

       ;

        createMainPageDto.getItemCode().forEach(itemCode -> {
            MainPage mainPage = new MainPage();
            mainPage.setMainPageComponent(mainPageComponent);
            mainPage.setItem(itemRepository.findByItemCode(itemCode).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND)));
            mainPageRepository.save(mainPage);
        });
        return new ResponseTemplate<>("");
    }

}
