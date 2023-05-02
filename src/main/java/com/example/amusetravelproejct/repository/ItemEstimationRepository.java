package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEstimationRepository extends JpaRepository<ItemReview, Long> {
}
