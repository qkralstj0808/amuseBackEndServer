package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.ResponseException;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplateStatus;
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


    private final ItemRepository itemRepository;

    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getTitle(findItem.getCountry(),findItem.getProvince(),
                findItem.getCity(), findItem.getTitle(), findItem.getRated()));
    }

    public ResponseTemplate<DetailPageResponse.getIcon> getIcon(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getIcon(findItem.getItemIcon_list().stream().map(
                itemIcon -> new DetailPageResponse.IconInfo(itemIcon.getIcon().getIcon(),itemIcon.getIcon().getText())
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getPicture(findItem.getItemImg_list().stream().map(itemImg ->
        itemImg.getImgUrl()).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getTicket(
                findItem.getItemTickets().stream().map(itemTicket ->
                        new DetailPageResponse.TicketInfo(itemTicket.getTitle()
                        ,itemTicket.getContent(),itemTicket.getPrice())).collect(Collectors.toList()),
                findItem.getItemPrices().stream().map(itemPrice ->
                        new DetailPageResponse.ProductPrice(
                                itemPrice.getStartDate(),itemPrice.getEndDate(),itemPrice.getPrice())).collect(Collectors.toList())));

    }

    public ResponseTemplate<DetailPageResponse.getContent> getContent(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getContent(findItem.getItemContents().stream().map(
                itemContent -> itemContent.getContent()
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getCourseContent(findItem.getItemCourses().stream().map(
                itemCourse -> new DetailPageResponse.CourseInfo(
                        itemCourse.getTitle(),itemCourse.getContent(),itemCourse.getSequenceId(),
                        itemCourse.getTimeCost(),itemCourse.getImageUrl(),itemCourse.getLatitude(),
                        itemCourse.getLongitude())
        ).collect(Collectors.toList())));
    }

    public ResponseTemplate<DetailPageResponse.getOtherContent> getOtherContent(Long product_id) {
        Item findItem = itemRepository.findById(product_id).orElseThrow(
                () -> new ResponseException(ResponseTemplateStatus.ITEM_NOT_FOUND)
        );

        return new ResponseTemplate<>(new DetailPageResponse.getOtherContent(findItem.getItemOtherContents().stream().map(
                itemOtherContent -> itemOtherContent.getContent()
        ).collect(Collectors.toList())));
    }
}
