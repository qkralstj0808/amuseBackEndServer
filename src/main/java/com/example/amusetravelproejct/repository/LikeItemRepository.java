package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.LikeItem;
import com.example.amusetravelproejct.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeItemRepository extends JpaRepository<LikeItem, Long>{
    List<LikeItem> findByUser(User user);
    void deleteByItem(Item item);
}
