package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.PageComponent;
import com.example.amusetravelproejct.repository.custom.PageComponentRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

import static com.example.amusetravelproejct.domain.QPageComponent.pageComponent;

public class PageComponentRepositoryImpl implements PageComponentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public PageComponentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PageComponent> findListByPageComponentIdList(List<Long> component_id_list) {
        return jpaQueryFactory.selectFrom(pageComponent)
                .where(pageComponent.id.in(component_id_list))
                .fetch();

    }
}
