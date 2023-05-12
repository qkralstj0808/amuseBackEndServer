package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.ItemTicketPrice;
import com.example.amusetravelproejct.repository.ItemCourseRepository;
import com.example.amusetravelproejct.repository.ItemTicketPriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ItemTicketPriceService {

    private final ItemTicketPriceRepository itemTicketPriceRepository;

    public void createItemTicketPrice(ItemTicketPrice itemTicketPrice) {
        itemTicketPriceRepository.save(itemTicketPrice);
    }



}
