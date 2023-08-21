package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemImg;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    @Query("SELECT img FROM item_img img JOIN img.item item ON img.item.id = item.id where item.id = :item_id ORDER BY " +
            "CASE WHEN img.sequence IS NULL THEN :last_sequence ELSE 0 END ,img.sequence ASC nulls last ")
    List<ItemImg> findItemImgByItemIdSortBySequenceAndNullLast(Long item_id,Integer last_sequence);

//    List<ItemImg> findByItemIdOrderBySequenceWithNullsLast(Long item_id);


//    ItemImg findTopByItemIdOOrderBySequenceAsc (Long item_id);
}
