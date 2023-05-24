package com.example.amusetravelproejct.repository.custom;

import com.example.amusetravelproejct.domain.ItemReview;

import java.util.List;

public interface ItemReviewRepositoryCustom {

    List<ItemReview> findByItemId(Long item_id);
}
