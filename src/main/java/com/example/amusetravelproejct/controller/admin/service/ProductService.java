package com.example.amusetravelproejct.controller.admin.service;

import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ItemRepository itemRepository;


//        public List<ItemTicket> saveItemTicket(List<ItemTicket> itemTickets, Long id){
//            itemTickets.forEach(itemTicket -> {
//                itemTicket.setItem(itemInforRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found with id " + id)));
//                itemTicketRepository.save(itemTicket);
//            });
//            return itemTickets;
//        }
//    @Transactional
//        public String saveItemImg(String img, Long id){
//        // TODO
//        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
//        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
//        // 반환
//
//        ItemImg itemImg = new ItemImg();
//        itemImg.setImgUrl(img);
//        itemImg.setItem(itemInforRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found with id " + id)));
//        imgRepository.save(itemImg);
//        return img;
//    }
//    @Transactional
//    public List<String> saveItemImg(List<String>imgs, Long id){
//        // TODO
//        // 멀티파트, base64로 받은 이미지 S3로 보내어 이미지 저장
//        // S3로 보내어 저장한 이미지의 url을 받아와서 List<String>에 저장
//        // 반환
//
//        imgs.forEach(img -> {
//            ItemImg itemImg = new ItemImg();
//            itemImg.setImgUrl(img);
//            itemImg.setItem(itemInforRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found with id " + id)));
//            imgRepository.save(itemImg);
//        });
//        return imgs;
//    }
//
//
//    public ItemCourse saveItemCourse(ItemCourse itemCourse) {
//        return itemCourseRepository.save(itemCourse);
//    }
//
//    public PaymentTicket savePaymentTicket(PaymentTicket paymentTicket) {
//        return paymentTicketRepository.save(paymentTicket);
//    }
//
//    public ItemTicket saveItemTicket(ItemTicket itemTicket) {
//        return itemTicketRepository.save(itemTicket);
//    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item not found with id " + id));
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }



}
