package com.example.amusetravelproejct.repository;

import com.example.amusetravelproejct.domain.ItemTicket;
import com.example.amusetravelproejct.domain.ItemTicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTicketPriceRepository extends JpaRepository<ItemTicketPrice, Long> {
    void deleteByItemTicket(ItemTicket itemTicket);
}
