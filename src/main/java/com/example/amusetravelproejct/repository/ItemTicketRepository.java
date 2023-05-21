package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemTicketRepository extends JpaRepository<ItemTicket, Long> {

    List<ItemTicket> findByItem(Item item);
}
