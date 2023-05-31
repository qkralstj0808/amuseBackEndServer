package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.MainPageComponent;
import com.example.amusetravelproejct.dto.request.MainPageRequest;

import java.util.List;

public interface MainPageRepositoryCustom {

    List<MainPageComponent> findItemInListsByMainPageRequestListDto();
}
