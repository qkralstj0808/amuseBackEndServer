package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemCourse;
import com.example.amusetravelproejct.repository.custom.ItemCourseRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.amusetravelproejct.domain.QItem.item;
import static com.example.amusetravelproejct.domain.QItemCourse.itemCourse;

public class ItemCourseRepositoryImpl implements ItemCourseRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    public ItemCourseRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ItemCourse> findItemCourseBySequence(Long item_id) {
        return jpaQueryFactory
                .select(itemCourse)
                .from(itemCourse)
                .join(itemCourse.item,item).fetchJoin()
                .where(item.id.eq(item_id))
                .orderBy(itemCourse.day.asc(),itemCourse.sequenceId.asc())
                .fetch();
    }
}
