package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.repository.custom.CategoryRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.amusetravelproejct.domain.QCategory.category;
import static com.example.amusetravelproejct.domain.QItem.item;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Category findByCategoryName(String category_name) {

        return jpaQueryFactory.selectFrom(category)
                .where(category.category_name.eq(category_name))
                .fetchOne();
    }

    @Override
    public List<Category> findgreaterSequence(Long sequence) {
        return jpaQueryFactory.selectFrom(category)
                .where(category.sequence.gt(sequence))
                .fetch();
    }

    @Override
    public List<Category> findAllByDisable(Boolean disable) {
        return jpaQueryFactory.selectFrom(category)
                .where(IdisNotNull(),eqDisable(disable))
                .fetch();
    }

    @Override
    public List<Category> findAllByDisableSortBySequence(Boolean disable) {
        return jpaQueryFactory.selectFrom(category)
                .where(IdisNotNull(),eqDisable(disable))
                .orderBy(category.sequence.asc())
                .fetch();
    }

    private BooleanExpression IdisNotNull() {
        return category.id.isNotNull();
    }

    private BooleanExpression eqDisable(Boolean disable) {
        if (disable == null) {
            return null;
        }
        return category.disable.eq(disable);
    }

}
