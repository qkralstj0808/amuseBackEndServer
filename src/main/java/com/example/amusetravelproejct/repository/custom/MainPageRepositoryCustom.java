package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.MainPage;
import com.example.amusetravelproejct.domain.PageComponent;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.dto.request.MainPageRequest;

import java.util.List;

public interface MainPageRepositoryCustom {

    List<PageComponent> findItemInListsByMainPageRequestListDto();

    List<Long> findTileIds(Long page_component_id);

    List<MainPage> findMainPageByComponent_idAndTyle_idAndDisplayTrueAndGrade(Long page_component_id, Long tile_id, Grade grade);

    List<MainPage> findMainPageByPageComponentIdAndItemDisplayWithGrade(Long pageComponent_id, Boolean item_display,Grade grade);
}
