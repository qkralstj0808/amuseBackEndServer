package com.example.amusetravelproejct.repository.customImpl;

import static com.example.amusetravelproejct.domain.QMainPage.mainPage;
import static com.example.amusetravelproejct.domain.QPageComponent.pageComponent;

import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.repository.custom.MainPageRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class MainPageRepositoryImpl implements MainPageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MainPageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PageComponent> findItemInListsByMainPageRequestListDto() {

        return jpaQueryFactory.select(pageComponent)
                .from(pageComponent)
                .where(pageComponent.type.eq("리스트"))
                .orderBy(pageComponent.id.asc())
                .groupBy(pageComponent)
                .fetch();
    }

    @Override
    public List<Long> findTileIds(Long page_component_id) {
        return jpaQueryFactory.select(mainPage.tile.id )
                .from(mainPage)
                .where(mainPage.pageComponent.id.eq(page_component_id))
                .groupBy(mainPage.tile)
                .fetch();
    }

    @Override
    public List<MainPage> findMainPageByComponent_idAndTyle_id(Long page_component_id, Long tile_id) {
        return jpaQueryFactory.selectFrom(mainPage)
                .where(mainPage.pageComponent.id.eq(page_component_id).and(mainPage.tile.id.eq(tile_id)))
                .fetch();
    }
}
