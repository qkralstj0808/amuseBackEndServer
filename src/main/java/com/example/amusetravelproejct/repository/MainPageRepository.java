package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.MainPage;
import com.example.amusetravelproejct.domain.PageComponent;
import com.example.amusetravelproejct.domain.Tile;
import com.example.amusetravelproejct.repository.custom.MainPageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainPageRepository extends JpaRepository<MainPage, Long>, MainPageRepositoryCustom {
    List<MainPage> findByPageComponent(PageComponent mainPageComponent);
    List<MainPage> findByTile(Tile tile);

    List<MainPage> findMainPageByTileId(Long tile_id);

    List<MainPage> findMainPageByPageComponentIdAndItemDisplay(Long pageComponent_id, Boolean item_display);

    List<MainPage> findByPageComponentIdOrderBySequence(Long pageComponent_id);

}
