package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemReview;
import com.example.amusetravelproejct.repository.custom.ItemReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReviewRepository extends JpaRepository<ItemReview,Long> , ItemReviewRepositoryCustom {
}
