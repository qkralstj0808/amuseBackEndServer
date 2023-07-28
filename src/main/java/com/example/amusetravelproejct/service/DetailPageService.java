package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.*;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.response.DetailPageResponse;
import com.example.amusetravelproejct.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailPageService {


    private final ItemRepository itemRepository;
    private final ItemCourseRepository itemCourseRepository;

    private final UserRepository userRepository;
    private final LikeItemRepository likeItemRepository;
    private final ItemReviewRepository itemReviewRepository;

    private User findUserById(String user_id){
        return userRepository.findByUserId(user_id);
    }

    private Item findItemById(Long item_id){
        return itemRepository.findById(item_id).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
    }

    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(Long item_id) {
        Item findItem = findItemById(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getTitle(findItem.getItemCode(),findItem.getCountry(),
                findItem.getCity(), findItem.getTitle(), findItem.getRated(),findItem.getReview_count(),findItem.getDuration()));
    }


    public ResponseTemplate<DetailPageResponse.getIcon> getIcon(Long item_id) {

        Item findItem = findItemById(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getIcon(findItem.getItemIcon_list().stream().map(
                itemIcon -> new DetailPageResponse.IconInfo(itemIcon.getIcon().getIconImgUrl(),itemIcon.getText())
        ).collect(Collectors.toList())));

    }


    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(Long item_id) {
        Item findItem = findItemById(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getPicture(findItem.getItemImg_list().stream().map(itemImg ->
        itemImg.getImgUrl()).collect(Collectors.toList())));
    }


    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(Long item_id) {
        Item findItem = findItemById(item_id);

        List<ItemTicket> itemTickets = findItem.getItemTickets();

        List<DetailPageResponse.TicketInfo> ticketInfos = itemTickets.stream().map(itemTicket ->
                new DetailPageResponse.TicketInfo(itemTicket.getId(),itemTicket.getContent(), itemTicket.getContent(),
                        itemTicket.getItemTicketPrices().stream().map(
                                itemTicketPrice -> new DetailPageResponse.TicketPrice(
                                        itemTicketPrice.getStartDate(), itemTicketPrice.getPrice()
                                )
                        ).collect(Collectors.toList()))).collect(Collectors.toList());

        return new ResponseTemplate<>(new DetailPageResponse.getTicket(ticketInfos));
    }


    public ResponseTemplate<DetailPageResponse.getContent> getContent(Long item_id) {
        Item findItem = findItemById(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getContent(findItem.getContent_1()));
    }


    public ResponseTemplate<DetailPageResponse.getManager> getManager(Long item_id) {
        Item findItem = findItemById(item_id);

        Guide guide = findItem.getGuide();

//        Admin findItemAdmin = findItem.getAdmin();

        if(guide == null){
            return new ResponseTemplate(null);
        }

        return new ResponseTemplate(new DetailPageResponse.getManager(guide.getId(),guide.getCode(),
                guide.getName(),guide.getEmile(),guide.getImgUrl(),guide.getIntroduce(),findItem.getGuide_comment()));
    }


    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(Long item_id) {
        Long findItem_id = itemRepository.findItem(item_id);

        if(findItem_id == null){
            return new ResponseTemplate(ResponseTemplateStatus.ITEM_NOT_FOUND);
        }


        List<ItemCourse> itemCourseBySequence = itemCourseRepository.findItemCourseBySequence(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getCourseContent(itemCourseBySequence.stream().map(
                itemCourse -> new DetailPageResponse.CourseInfo(
                        itemCourse.getTitle(),itemCourse.getContent(),itemCourse.getDay(),itemCourse.getSequenceId(),
                        itemCourse.getTimeCost(),itemCourse.getImageUrl(),itemCourse.getLatitude(),
                        itemCourse.getLongitude())
        ).collect(Collectors.toList())));
    }


    public ResponseTemplate<DetailPageResponse.getOtherContent> getOtherContent(Long item_id) {
        Item findItem = findItemById(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getOtherContent(findItem.getContent_2()));
    }

    @Transactional
    public ResponseTemplate<DetailPageResponse.setLike> setLikePlus(Long item_id,String user_id) {
        User findUser = findUserById(user_id);
        Item findItem = findItemById(item_id);

        // 이미 좋아요를 누른 상품인지 아닌지 확인
        if(getUserLikeItem(findItem,findUser) != null){
            throw new CustomException(ErrorCode.EXIT_LIKE_ITEM);
        }

        findItem.plus_like();
        LikeItem likeItem = LikeItem.builder()
                .item(findItem)
                .user(findUser)
                .build();
        likeItemRepository.save(likeItem);

        return new ResponseTemplate(new DetailPageResponse.setLike(findItem.getLike_num()));


    }

    @Transactional
    public ResponseTemplate<DetailPageResponse.setLike> setLikeMinus(Long item_id, String user_id) {
        User findUser = findUserById(user_id);
        Item findItem = findItemById(item_id);

        LikeItem likeItem = getUserLikeItem(findItem, findUser);

        // 이미 좋아요를 누른 상품인지 아닌지 확인
        if(likeItem == null){
            throw new CustomException(ErrorCode.NOT_EXIT_LIKE_ITEM);
        }

        findItem.minus_like();
        findUser.deleteLikeItem(likeItem);
        return new ResponseTemplate(new DetailPageResponse.setLike(findItem.getLike_num()));
    }

    private LikeItem getUserLikeItem(Item findItem, User findUser){

        List<LikeItem> likeItems = findUser.getLikeItems();
        if(likeItems != null){
            for(int i = 0 ; i < likeItems.size() ; i++){
                if(likeItems.get(i).getItem().equals(findItem)){
                    return likeItems.get(i);
                }
            }
        }

        return null;
    }

    public ResponseTemplate<DetailPageResponse.getReview> getReview(Item findItem) {

        List<ItemReview> findItemReviews = findItem.getItemReviews();

        Integer reviewCount = findItemReviews.size();

        Double sum_rating = 0.;
        Double rate = 0.;

        List<String> itemReviewImgList = new ArrayList<>();

        if(reviewCount != 0){
            for(int i = 0 ; i < reviewCount ; i++){
                sum_rating += (double)findItemReviews.get(i).getRating();
                if(findItemReviews.get(i).getItemReviewImgs().size() != 0){
                    for(int j = 0 ; j < findItemReviews.get(i).getItemReviewImgs().size();j++){
                        itemReviewImgList.add(findItemReviews.get(i).getItemReviewImgs().get(j).getImgUrl());
                    }
                }
            }
            rate = sum_rating/reviewCount;
        }else{
            rate = 0.;
            itemReviewImgList = null;
            if(findItemReviews.size() != 0){
                return  new ResponseTemplate(new DetailPageResponse.getReview(rate,reviewCount,null,findItemReviews.stream().map(
                        itemReview -> new DetailPageResponse.ReviewInfo(itemReview.getUser().getUsername(),
                                itemReview.getContent(),itemReview.getRating(),itemReview.getCreatedDate(),null)).collect(Collectors.toList())));
            }else{
                return  new ResponseTemplate(new DetailPageResponse.getReview(rate,reviewCount,null,null));
            }


        }

        return new ResponseTemplate(new DetailPageResponse.getReview(rate,reviewCount,
                itemReviewImgList.stream().map(
                        imgString -> new DetailPageResponse.ReviewImage(imgString)
                ).collect(Collectors.toList()),
                findItemReviews.stream().map(
                        itemReview -> new DetailPageResponse.ReviewInfo(itemReview.getUser().getUsername(),
                                itemReview.getContent(),itemReview.getRating(),itemReview.getCreatedDate(),itemReview.getItemReviewImgs().stream().map(
                                        itemReviewImg -> new DetailPageResponse.ReviewImage(itemReviewImg.getImgUrl())
                        ).collect(Collectors.toList()))
                ).collect(Collectors.toList())));
    }

    @Transactional
    public void updateRatingReviewCount(Double rated,Integer review_count,Item findItem){
        findItem.setRated(rated);
        findItem.setReview_count(review_count);
        itemRepository.saveAndFlush(findItem);

    }

}
