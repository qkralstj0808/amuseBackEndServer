package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.*;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.domain.ItemCourse;
import com.example.amusetravelproejct.dto.response.DetailPageResponse;
import com.example.amusetravelproejct.repository.ItemCourseRepository;
import com.example.amusetravelproejct.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailPageService {


    private final ItemRepository itemRepository;
    private final ItemCourseRepository itemCourseRepository;

    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(Long item_id) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getTitle(findItem.getCountry(),
                findItem.getCity(), findItem.getTitle(), findItem.getRated()));
    }


    public ResponseTemplate<DetailPageResponse.getIcon> getIcon(Long item_id) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );
        return null;
//
//        return new ResponseTemplate<>(new DetailPageResponse.getIcon(findItem.getItemIcon_list().stream().map(
//                itemIcon -> new DetailPageResponse.IconInfo(itemIcon.getIcon(),itemIcon.getText())
//        ).collect(Collectors.toList())));
    }


    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(Long item_id) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getPicture(findItem.getItemImg_list().stream().map(itemImg ->
        itemImg.getImgUrl()).collect(Collectors.toList())));
    }


    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(Long item_id) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getTicket(
                findItem.getItemTickets().stream().map(itemTicket ->
                        new DetailPageResponse.TicketInfo(itemTicket.getTitle()
                        ,itemTicket.getContent(),itemTicket.getItemTicketPrices().stream().map(
                                itemTicketPrice -> new DetailPageResponse.TicketPrice(
                                        itemTicketPrice.getStartDate().toString(),itemTicketPrice.getPrice().toString()
                                )
                        ).collect(Collectors.toList()))).collect(Collectors.toList()) ));
    }


    public ResponseTemplate<DetailPageResponse.getContent> getContent(Long item_id) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getContent(findItem.getContent_1()));
    }


    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(Long item_id) {
        Long findItem_id = itemRepository.findItem(item_id);

        if(findItem_id == null){
            return new ResponseTemplate(ResponseTemplateStatus.ITEM_NOT_FOUND);
        }


        List<ItemCourse> itemCourseBySequence = itemCourseRepository.findItemCourseBySequence(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getCourseContent(itemCourseBySequence.stream().map(
                itemCourse -> new DetailPageResponse.CourseInfo(
                        itemCourse.getTitle(),itemCourse.getContent(),itemCourse.getSequenceId(),
                        itemCourse.getTimeCost(),itemCourse.getImageUrl(),itemCourse.getLatitude(),
                        itemCourse.getLongitude())
        ).collect(Collectors.toList())));
    }


    public ResponseTemplate<DetailPageResponse.getOtherContent> getOtherContent(Long item_id) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getOtherContent(findItem.getContent_2()));
    }
}
