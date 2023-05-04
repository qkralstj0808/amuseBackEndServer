package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemTicket;
import com.example.amusetravelproejct.repository.ItemTicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ItemTicketService {
    private final ItemTicketRepository itemTicketRepository;

//    public ItemTicketsaveItemTicket(List<ItemTicket> itemTickets, Item item){
//            itemTickets.forEach(itemTicket -> {
//                itemTicket.setItem(item);
//                itemTicketRepository.save(itemTicket);
//            });
//            return itemTickets;
//        }
    public ItemTicket saveItemTicket(ItemTicket itemTicket) {
        return itemTicketRepository.save(itemTicket);
    }
}
