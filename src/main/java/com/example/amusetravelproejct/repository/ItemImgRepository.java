package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderBySequence(Long item_id);

    ItemImg findFirstByItemIdOrderBySequence(Long item_id);
}
