package com.example.amusetravelproejct.repository.customImpl;

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
//    return null;

}
