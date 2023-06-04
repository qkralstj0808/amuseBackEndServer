package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.Category;
import com.example.amusetravelproejct.repository.custom.CategoryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.example.amusetravelproejct.domain.QCategory.category;

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
}
