package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.*;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.response.DetailPageResponse;
import com.example.amusetravelproejct.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailPageService {

    private final ItemRepository itemInforRepository;

    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getTitle(findItem.getCountry(),findItem.getCity(),
                findItem.getCity(), findItem.getTitle(), findItem.getRated()));
    }

    public ResponseTemplate<DetailPageResponse.getIcon> getIcon(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getIcon(findItem.getItemIcon_list().stream().map(
                itemIcon -> new DetailPageResponse.IconInfo(itemIcon.getIcon().getIcon(),itemIcon.getIcon().getText())
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getPicture(findItem.getItemImg_list().stream().map(itemImg ->
        itemImg.getImgUrl()).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getTicket(
                findItem.getItemTickets().stream().map(itemTicket ->
                        new DetailPageResponse.TicketInfo(itemTicket.getTitle()
                        ,itemTicket.getContent(),itemTicket.getItemTicketPrices().stream().map(
                                itemTicketPrice -> new DetailPageResponse.TicketPrice(
                                        itemTicketPrice.getStartDate(),itemTicketPrice.getPrice()
                                )
                        ).collect(Collectors.toList()))).collect(Collectors.toList()) ));
    }

    public ResponseTemplate<DetailPageResponse.getContent> getContent(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getContent(findItem.getContent_1()));
    }

    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getCourseContent(findItem.getItemCourses().stream().map(
                itemCourse -> new DetailPageResponse.CourseInfo(
                        itemCourse.getTitle(),itemCourse.getContent(),itemCourse.getSequenceId(),
                        itemCourse.getTimeCost(),itemCourse.getImageUrl(),itemCourse.getLatitude(),
                        itemCourse.getLongitude())
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getOtherContent> getOtherContent(Long product_id) {
        Item findItem = itemInforRepository.findById(product_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getOtherContent(findItem.getContent_2()));
    }
}
