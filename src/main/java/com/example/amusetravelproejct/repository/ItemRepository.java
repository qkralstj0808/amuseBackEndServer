package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.repository.custom.ItemRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

}
