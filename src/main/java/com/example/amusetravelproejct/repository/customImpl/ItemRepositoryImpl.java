package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.amusetravelproejct.domain.QItem.item;
import static com.example.amusetravelproejct.domain.QItemHashTag.itemHashTag;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Long findItem(Long item_id) {
        return jpaQueryFactory
                .select(item.id)
                .from(item)
                .where(item.id.eq(item_id))
                .fetchOne();
    }

    @Override
    public List<Item> find10ItemByCondition(String country, String city, String title, String content_1, String content_2) {
        return null;
    }

    @Override
    public List<Item> find10BestItem() {
        return jpaQueryFactory.selectFrom(item)
                .orderBy(item.createdDate.desc())
                .orderBy(item.like_num.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public List<Item> find10CurrentItem() {
        return jpaQueryFactory.selectFrom(item)
                .orderBy(item.createdDate.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public List<Item> find10CategoryBestItem(String category) {
        return jpaQueryFactory.selectFrom(item)
                .where(item.id.in(JPAExpressions
                        .select(itemHashTag.item.id)
                        .from(itemHashTag)
                        .where(itemHashTag.hashTag.eq(category))))
                .orderBy(item.like_num.desc())
                .offset(0)
                .limit(10)
                .fetch();

    }

    @Override
    public List<Item> find10CategoryCurrentItem(String category) {
        return jpaQueryFactory.selectFrom(item)
                .where(item.id.in(JPAExpressions
                        .select(itemHashTag.item.id)
                        .from(itemHashTag)
                        .where(itemHashTag.hashTag.eq(category))))
                .orderBy(item.createdDate.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public Page<Item> findCategoryBestItemPage(String category, Pageable pageable) {
        List<Item> content = jpaQueryFactory.selectFrom(item)
                .where(item.id.in(JPAExpressions
                        .select(itemHashTag.itemHashTag.item.id)
                        .from(itemHashTag)
                        .where(itemHashTag.hashTag.eq(category))))
                .orderBy(item.like_num.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory.select(item.count())
                .from(item)
                .where(item.id.in(JPAExpressions
                        .select(itemHashTag.item.id)
                        .from(itemHashTag)
                        .where(itemHashTag.hashTag.eq(category))))
                .fetchOne();

        return new PageImpl<>(content,pageable, total);
    }

    @Override
    public Page<Item> findCategoryCurrentItemPage(String category, Pageable pageable) {
        List<Item> content = jpaQueryFactory.selectFrom(item)
                .where(item.id.in(JPAExpressions
                        .select(itemHashTag.item.id)
                        .from(itemHashTag)
                        .where(itemHashTag.hashTag.eq(category))))
                .orderBy(item.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory.select(item.count())
                .from(item)
                .where(item.id.in(JPAExpressions
                        .select(itemHashTag.item.id)
                        .from(itemHashTag)
                        .where(itemHashTag.hashTag.eq(category))))
                .fetchOne();

        return new PageImpl<>(content,pageable, total);


    }
}
