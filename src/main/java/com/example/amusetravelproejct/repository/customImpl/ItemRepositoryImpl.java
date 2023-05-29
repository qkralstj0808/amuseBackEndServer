package com.example.amusetravelproejct.repository.customImpl;

import com.example.amusetravelproejct.config.util.QueryDslUtil;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.ItemSearchRequest;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.example.amusetravelproejct.domain.QItem.item;
import static com.example.amusetravelproejct.domain.QItemHashTag.itemHashTag;

@Slf4j
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
    public List<Long> findAllItemId() {
        return jpaQueryFactory.select(item.id)
                .from(item)
                .fetch();
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

    @Override
    public Page<Item> findItemByCondition(ItemSearchRequest.ItemConditionDto itemConditionDto, Pageable pageable) {
        List<OrderSpecifier> ORDERS = createOrderSpecifier(pageable);

        List<Item> itemList = jpaQueryFactory.selectFrom(item)
                .where(IdisNotNull(),
                        eqCountry(itemConditionDto.getCountry()),
                        eqCity(itemConditionDto.getCity()),
                        eqDuration(itemConditionDto.getDuration()))
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory.select(item.count())
                .from(item)
                .where(IdisNotNull(),
                        eqCountry(itemConditionDto.getCountry()),
                        eqCity(itemConditionDto.getCity()),
                        eqDuration(itemConditionDto.getDuration()))
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return new PageImpl<>(itemList, pageable, total);
    }



    @Override
    public Page<Item> searchItemByWordAndSort(String[] contain_words, Pageable pageable) {
        List<OrderSpecifier> ORDERS = createOrderSpecifier(pageable);
        log.info(ORDERS.toString());

        if(contain_words == null){
            List<Item> itemList = jpaQueryFactory.selectFrom(item)
                    .orderBy(ORDERS.toArray(OrderSpecifier[]::new))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            Long total = jpaQueryFactory.select(item.count())
                    .from(item)
                    .fetchOne();

            return new PageImpl<>(itemList, pageable, total);
        }

        List<Long> hashTagQuery = jpaQueryFactory.select(item.id)
                .from(item)
                .join(item.itemHashTags, itemHashTag)
                .where(containHashTag(contain_words))
                .fetch();

        List<Long> titleQuery = jpaQueryFactory.select(item.id)
                .from(item)
                .join(item.itemHashTags, itemHashTag)
                .where(notContainHashTag(contain_words).and(list_containTitle(contain_words)))
                .fetch();


        List<Long> contentQuery = jpaQueryFactory.select(item.id)
                .from(item)
                .join(item.itemHashTags, itemHashTag)
                .where(notContainHashTag(contain_words).and(list_notContainTitle(contain_words)).and(list_containContent1_or_Content2(contain_words)))
                .fetch();


        List<Item> itemList = jpaQueryFactory.selectFrom(item)
                .where(IdisNotNull())
                .orderBy(new CaseBuilder()
                        .when(item.id.in(hashTagQuery)).then(0)
                        .when(item.id.in(titleQuery)).then(1)
                        .when(item.id.in(contentQuery)).then(2)
                        .otherwise(3)
                        .asc())
                .orderBy(ORDERS.toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<Long> combinedQuery = new ArrayList<>();
        combinedQuery.addAll(hashTagQuery);
        combinedQuery.addAll(titleQuery);
        combinedQuery.addAll(contentQuery);

        log.info("pageable.getOffset : " + pageable.getOffset());
        log.info("pageable.getPageSize : " + pageable.getPageSize());


        log.info("total : " + combinedQuery.size());

        return new PageImpl<>(itemList, pageable, combinedQuery.size());
    }

    private BooleanExpression containTitle(String contain_word) {
        if (StringUtils.isBlank(contain_word)) {
            return null;
        }
        return item.title.containsIgnoreCase(contain_word);
    }

    private BooleanExpression notContainTitle(String contain_word) {
        if (StringUtils.isBlank(contain_word)) {
            return null;
        }
        return item.title.containsIgnoreCase(contain_word).not();
    }

    private BooleanExpression list_containTitle(String[] contain_words){
        int count = 0;
        BooleanExpression booleanExpression = null;

        if(contain_words == null){
            return null;
        }

        for(String contain_word : contain_words){
            if(count == 0){     // 첫번째 단어이면
                log.info(contain_word);
                booleanExpression = containTitle(contain_word);
                count = 1;
            }else{
                booleanExpression = booleanExpression.or(containTitle(contain_word));
            }
        }

        return booleanExpression;
    }

    private BooleanExpression list_notContainTitle(String[] contain_words){
        int count = 0;
        BooleanExpression booleanExpression = null;
        for(String contain_word : contain_words){
            if(count == 0){     // 첫번째 단어이면
                log.info(contain_word);
                booleanExpression = notContainTitle(contain_word);
                count = 1;
            }else{
                booleanExpression = booleanExpression.or(notContainTitle(contain_word));
            }
        }

        return booleanExpression;
    }

    private BooleanExpression containContent1(String contain_word) {
        if (StringUtils.isBlank(contain_word)) {
            return null;
        }
        return item.content_1.containsIgnoreCase(contain_word);
    }

    private BooleanExpression list_contanContent1(String[] contain_words){
        int count = 0;
        BooleanExpression booleanExpression = null;
        for(String contain_word : contain_words){
            if(count == 0){     // 첫번째 단어이면
                booleanExpression = containContent1(contain_word);
                count = 1;
            }else{
                booleanExpression = booleanExpression.or(containContent1(contain_word));
            }
        }

        return booleanExpression;
    }

    private BooleanExpression containContent2(String contain_word) {
        if (StringUtils.isBlank(contain_word)) {
            return null;
        }
        return item.content_2.containsIgnoreCase(contain_word);
    }

    private BooleanExpression list_contanContent2(String[] contain_words){
        int count = 0;
        BooleanExpression booleanExpression = null;
        for(String contain_word : contain_words){
            if(count == 0){     // 첫번째 단어이면
                booleanExpression = containContent2(contain_word);
                count = 1;
            }else{
                booleanExpression = booleanExpression.or(containContent2(contain_word));
            }
        }

        return booleanExpression;
    }

    private BooleanExpression list_containContent1_or_Content2(String[] contain_words){
        return list_contanContent1(contain_words).or(list_contanContent2(contain_words));
    }

    private BooleanExpression containHashTag(String[] contain_word) {
        if (contain_word == null) {
            return null;
        }
        return itemHashTag.hashTag.in(contain_word);
    }

    private BooleanExpression notContainHashTag(String[] contain_word) {
        if (contain_word == null) {
            return null;
        }
        return itemHashTag.hashTag.notIn(contain_word);
    }


    private List<OrderSpecifier> createOrderSpecifier(Pageable pageable) {

        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if(!pageable.getSort().isEmpty()){
            for(Sort.Order order : pageable.getSort()){
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "rated" :
                        OrderSpecifier<?> orderRated = QueryDslUtil.getSortedColumn(direction, item.rated, "rated");
                        ORDERS.add(orderRated);
                        break;
                    case "like_num" :
                        OrderSpecifier<?> orderLikeNum = QueryDslUtil.getSortedColumn(direction, item.like_num, "like_num");
                        ORDERS.add(orderLikeNum);
                        break;
                    case "startPrice" :
                        OrderSpecifier<?> orderStartPrice = QueryDslUtil.getSortedColumn(direction, item.startPrice, "startPrice");
                        ORDERS.add(orderStartPrice);
                        break;
                    case "date" :
                        OrderSpecifier<?> orderDate = QueryDslUtil.getSortedColumn(direction, item.modifiedDate, "modifiedDate");
                        ORDERS.add(orderDate);
                        break;
                    default :
                        break;

                }
            }
        }

        return ORDERS;
    }

    private BooleanExpression IdisNotNull() {
        return item.id.isNotNull();
    }

    private BooleanExpression eqCountry(String country) {
        if (StringUtils.isBlank(country)) {
            return null;
        }
        return item.title.containsIgnoreCase(country);
    }

    private BooleanExpression eqCity(String city) {
        if (StringUtils.isBlank(city)) {
            return null;
        }
        return item.city.containsIgnoreCase(city);
    }

    private BooleanExpression eqDuration(Integer duration) {
        if (duration == null) {
            return null;
        }
        return item.duration.eq(duration);
    }



}
