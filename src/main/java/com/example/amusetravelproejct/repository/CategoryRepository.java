package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<ItemHashTag, Long> {

}
