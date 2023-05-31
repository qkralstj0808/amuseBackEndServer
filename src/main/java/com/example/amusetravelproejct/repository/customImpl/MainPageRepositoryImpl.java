package com.example.amusetravelproejct.repository.customImpl;

import static com.example.amusetravelproejct.domain.QMainPageComponent.mainPageComponent;
import static com.example.amusetravelproejct.domain.QMainPage.mainPage;
import static com.example.amusetravelproejct.domain.QItem.item;

import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.*;
import com.example.amusetravelproejct.repository.custom.MainPageRepositoryCustom;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QList;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;

public class MainPageRepositoryImpl implements MainPageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MainPageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<MainPageComponent> findItemInListsByMainPageRequestListDto() {

        return jpaQueryFactory.select(mainPageComponent)
                .from(mainPageComponent)
                .where(mainPageComponent.type.eq("리스트"))
                .orderBy(mainPageComponent.sequence.asc())
                .groupBy(mainPageComponent)
                .fetch();
    }
//    return null;

}
