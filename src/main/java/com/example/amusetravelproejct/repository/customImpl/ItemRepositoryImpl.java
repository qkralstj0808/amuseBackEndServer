package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.bytebuddy.asm.Advice;
import org.hibernate.criterion.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.amusetravelproejct.domain.QItemImg.itemImg;
import static com.example.amusetravelproejct.domain.QCategory.category;
import static com.example.amusetravelproejct.domain.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Item> find10BestItem() {

        return jpaQueryFactory.selectFrom(item)
//                .orderBy(item.createdDate.desc())
                .orderBy(item.like_num.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public List<Item> find10CurrentItem() {
        return jpaQueryFactory.selectFrom(item)
//                .orderBy(item.createdDate.desc())
                .offset(0)
                .limit(10)
                .fetch();
    }

    @Override
    public List<Item> find10CategoryBestItem(Long category_id) {
        return jpaQueryFactory.select(item)
                .from(item)
                .where(item.category.id.eq(category_id))
                .orderBy(item.like_num.desc())
                .offset(1)
                .limit(10)
                .fetch();
    }

    @Override
    public List<Item> find10CategoryCurrentItem(Long category_id) {
        return jpaQueryFactory.selectFrom(item)
                .orderBy(item.createdDate.desc())
                .where(item.category.id.eq(category_id))
                .offset(1)
                .limit(10)
                .fetch();
    }

    @Override
    public Page<Item> findCategoryAllItemPage(Long category_id, Pageable pageable) {
        QueryResults<Item> itemQueryResults = jpaQueryFactory.selectFrom(item)
                .orderBy(item.like_num.desc(), item..desc())
                .where(item.category.id.eq(category_id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return null;
    }
}
