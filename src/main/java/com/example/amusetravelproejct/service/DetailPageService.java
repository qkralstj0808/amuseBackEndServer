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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    private final ItemImgRepository itemImgRepository;

    private User findUserById(String user_id){
        return userRepository.findByUserId(user_id);
    }

    private Item findItemByIdAndDisplayTrue(Long item_id){
        return itemRepository.findByIdAndDisplayTrue(item_id).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));
    }

    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(Long item_id) {
        Item findItem = findItemByIdAndDisplayTrue(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getTitle(findItem.getItemCode(),findItem.getItemType(),findItem.getCountry(),
                findItem.getCity(), findItem.getTitle(), findItem.getRated(),findItem.getReview_count(),findItem.getDuration(),
                findItem.getStartPrice(),findItem.getStartPoint(),findItem.getRunningTime(),findItem.getActivityIntensity(),findItem.getLanguage()));
    }


    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(Long item_id) {
        Item findItem = findItemByIdAndDisplayTrue(item_id);

        ItemImg LastItem = itemImgRepository.findFirstByItemIdOrderBySequenceDesc(findItem.getId());
        List<ItemImg> itemImgs = itemImgRepository.findItemImgByItemIdSortBySequenceAndNullLast(item_id,LastItem == null || LastItem.getSequence() == null ? findItem.getItemImg_list().size() : LastItem.getSequence()+1);
//        return new ResponseTemplate<>(new DetailPageResponse.getPicture(itemImgs.stream().map(itemImg ->
//        itemImg.getImgUrl()).collect(Collectors.toList())));
        return new ResponseTemplate<>(new DetailPageResponse.getPicture(itemImgs.stream().map(itemImg -> new DetailPageResponse.ImageInfo(
                itemImg.getId(),
                itemImg.getImgUrl(),
                itemImg.getSequence())).collect(Collectors.toList())));
    }


    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(Long item_id) {
        Item findItem = findItemByIdAndDisplayTrue(item_id);

        List<ItemTicket> itemTickets = findItem.getItemTickets();

        List<DetailPageResponse.TicketInfo> ticketInfos = itemTickets.stream().map(itemTicket ->
                new DetailPageResponse.TicketInfo(itemTicket.getId(),itemTicket.getTitle(), itemTicket.getContent(), itemTicket.getSequenceNum(),
                        itemTicket.getItemTicketPrices().stream().map(
                                itemTicketPrice -> new DetailPageResponse.TicketPrice(
                                        itemTicketPrice.getStartDate(), itemTicketPrice.getPrice()
                                )
                        ).collect(Collectors.toList()))).collect(Collectors.toList());

        //
        Collections.sort(ticketInfos, Comparator.comparing(DetailPageResponse.TicketInfo::getSequenceNum,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return new ResponseTemplate<>(new DetailPageResponse.getTicket(ticketInfos));
    }


    public ResponseTemplate<DetailPageResponse.getContent> getContent(Long item_id) {
        Item findItem = findItemByIdAndDisplayTrue(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getContent(findItem.getContent_1()));
    }


    public ResponseTemplate<DetailPageResponse.getManager> getManager(Long item_id) {
        Item findItem = findItemByIdAndDisplayTrue(item_id);

        Guide guide = findItem.getGuide();

//        Admin findItemAdmin = findItem.getAdmin();

        if(guide == null){
            return new ResponseTemplate(new DetailPageResponse.getManager(null,null,null,null,null,null,null));
        }

        return new ResponseTemplate(new DetailPageResponse.getManager(guide.getId(),guide.getCode(),
                guide.getName(),guide.getEmail(),guide.getImgUrl(),guide.getIntroduce(),findItem.getGuide_comment()));
    }


    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(Long item_id) {
        Long findItem_id = itemRepository.findItemId(item_id);

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
        Item findItem = findItemByIdAndDisplayTrue(item_id);

        return new ResponseTemplate<>(new DetailPageResponse.getOtherContent(findItem.getContent_2()));
    }

    @Transactional
    public ResponseTemplate<DetailPageResponse.setLike> setLikePlus(Long item_id,String user_id) {
        User findUser = findUserById(user_id);
        Item findItem = findItemByIdAndDisplayTrue(item_id);

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
        Item findItem = findItemByIdAndDisplayTrue(item_id);

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

        int reviewCount = findItemReviews.size();
        Double rate = findItem.getRated();

        List<String> itemReviewImgList = new ArrayList<>();

        // 상품에 리뷰가 있다면
        if(reviewCount != 0){
            for(int i = 0 ; i < reviewCount ; i++){
                if(findItemReviews.get(i).getItemReviewImgs().size() != 0){
                    for(int j = 0 ; j < findItemReviews.get(i).getItemReviewImgs().size();j++){
                        itemReviewImgList.add(findItemReviews.get(i).getItemReviewImgs().get(j).getImgUrl());
                    }
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

        }else{  // 상품에 리뷰가 없다면
            return  new ResponseTemplate(new DetailPageResponse.getReview(rate,reviewCount,null,null));
        }


    }

    @Transactional
    public void updateRatingReviewCount(Double rated,Integer review_count,Item findItem){
        findItem.setRated(rated);
        findItem.setReview_count(review_count);
        itemRepository.saveAndFlush(findItem);

    }

}
