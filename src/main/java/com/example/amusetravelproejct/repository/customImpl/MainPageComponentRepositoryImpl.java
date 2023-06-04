package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.PageComponent;
import com.example.amusetravelproejct.repository.custom.MainPageComponentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.amusetravelproejct.domain.QPageComponent.pageComponent;

public class MainPageComponentRepositoryImpl implements MainPageComponentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MainPageComponentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PageComponent> findByTypeSortSequence(String type) {
        return jpaQueryFactory.selectFrom(pageComponent)
                .where(pageComponent.type.eq(type))
                .orderBy(pageComponent.id.desc())
                .fetch();
    }
}
