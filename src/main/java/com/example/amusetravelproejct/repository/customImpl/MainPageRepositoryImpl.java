package com.example.amusetravelproejct.repository.customImpl;

import static com.example.amusetravelproejct.domain.QItem.item;
import static com.example.amusetravelproejct.domain.QMainPage.mainPage;
import static com.example.amusetravelproejct.domain.QPageComponent.pageComponent;

import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.repository.custom.MainPageRepositoryCustom;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
    public List<MainPage> findMainPageByComponent_idAndTyle_idAndDisplayTrueAndGrade(Long page_component_id, Long tile_id,Grade grade) {
        return jpaQueryFactory.selectFrom(mainPage)
                .where(eqComponentId(page_component_id),eqMainPageTileId(tile_id),BeteweenBronzeAndMyGradeItem(grade),ItemDisplay(true))
                .fetch();
    }

    @Override
    public List<MainPage> findMainPageByPageComponentIdAndItemDisplayWithGrade(Long pageComponent_id, Boolean item_display,Grade grade) {
        return jpaQueryFactory.selectFrom(mainPage)
                .where(eqComponentId(pageComponent_id),ItemDisplay(true),BeteweenBronzeAndMyGradeItem(grade))
                .fetch();
    }

    private BooleanExpression eqComponentId(Long page_component_id){
        return mainPage.pageComponent.id.eq(page_component_id);
    }

    private BooleanExpression eqMainPageTileId(Long tile_id){
        return mainPage.tile.id.eq(tile_id);
    }


    private BooleanExpression BeteweenBronzeAndMyGradeItem(Grade grade) {
        if(mainPage.item.grade == null){
            return Expressions.TRUE;
        }

        if(StringUtils.isBlank(grade.toString())){
            return Expressions.TRUE;
        }

        return mainPage.item.grade.between(Grade.BRONZE,grade);
    }

    private BooleanExpression ItemDisplay(Boolean display){
        return mainPage.item.display.eq(display);
    }

}
