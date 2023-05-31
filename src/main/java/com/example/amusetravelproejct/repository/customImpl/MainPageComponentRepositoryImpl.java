package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.MainPageComponent;
import static com.example.amusetravelproejct.domain.QMainPageComponent.mainPageComponent;
import com.example.amusetravelproejct.repository.custom.MainPageComponentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class MainPageComponentRepositoryImpl implements MainPageComponentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public MainPageComponentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<MainPageComponent> findByTypeSortSequence(String type) {
        return jpaQueryFactory.selectFrom(mainPageComponent)
                .where(mainPageComponent.type.eq(type))
                .orderBy(mainPageComponent.sequence.desc())
                .fetch();
    }
}
