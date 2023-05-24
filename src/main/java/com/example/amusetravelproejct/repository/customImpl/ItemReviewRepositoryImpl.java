package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.ItemReview;
import com.example.amusetravelproejct.repository.custom.ItemReviewRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.amusetravelproejct.domain.QItem.item;
import static com.example.amusetravelproejct.domain.QItemReview.itemReview;

public class ItemReviewRepositoryImpl implements ItemReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemReviewRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<ItemReview> findByItemId(Long item_id) {
        return null;
    }
}
