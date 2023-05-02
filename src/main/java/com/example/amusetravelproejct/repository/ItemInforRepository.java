package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemInforRepository extends JpaRepository<Item, Long> {


}
