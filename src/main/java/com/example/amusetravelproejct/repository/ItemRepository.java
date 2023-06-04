package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.person_enum.DisplayStatus;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom, QuerydslPredicateExecutor<Item> {

    Optional<Item> findByItemCode(String itemCode);
    Page<Item> findAllByDisplayStatus(DisplayStatus displayStatus, Pageable pageable);

}
