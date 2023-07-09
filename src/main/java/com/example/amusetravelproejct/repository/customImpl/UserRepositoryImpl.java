package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.repository.custom.UserRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.example.amusetravelproejct.domain.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<User> findUserListByEmail(String email) {

        return jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetch();
    }
}
