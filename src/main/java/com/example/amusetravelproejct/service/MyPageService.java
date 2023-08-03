package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.*;
import com.example.amusetravelproejct.dto.request.MyPageRequest;
import com.example.amusetravelproejct.dto.response.MyPageResponse;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.repository.ItemReviewImgRepository;
import com.example.amusetravelproejct.repository.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final ItemReviewImgRepository itemReviewImgRepository;
    private final ItemReviewRepository itemReviewRepository;
    private final ItemRepository itemRepository;

    public ResponseTemplate<MyPageResponse.getLikeItems> getLikeItems(User findUser) {

        List<Item> findLikeItems = findUser.getLikeItems().stream().map(LikeItem::getItem).collect(Collectors.toList());

        List<MyPageResponse.ItemInfo> itemInfoList = new ArrayList<>();
        for (Item likeItem : findLikeItems) {
            String item_img = null;

            if (likeItem.getItemImg_list().size() != 0) {
                item_img = likeItem.getItemImg_list().get(0).getImgUrl();
            }
            itemInfoList.add(new MyPageResponse.ItemInfo(likeItem.getId(), likeItem.getItemCode(),
                    item_img, likeItem.getTitle(), likeItem.getCountry(), likeItem.getCity(), likeItem.getDuration(),
                    likeItem.getLike_num(), likeItem.getStartPrice()));
        }

        return new ResponseTemplate(new MyPageResponse.getLikeItems(itemInfoList));
    }

    @Transactional
    public ResponseTemplate<MyPageResponse.setReview> setReview(User findUser, Long item_id,MyPageRequest.setReview request,
                                                                UtilMethod utilMethod) {
        Item findItem = itemRepository.findById(item_id).orElseThrow(
                () -> new CustomException(ErrorCode.ITEM_NOT_FOUND)
        );

        ItemReview itemReview = new ItemReview();
        itemReview.setItem(findItem);
        itemReview.setUser(findUser);
        itemReview.setContent(request.getReview_content());
        itemReview.setRating(request.getRate());
        List<ItemReviewImg> itemReviewImgList = processItemImg(request, utilMethod, itemReview);
        itemReview.setItemReviewImgs(itemReviewImgList);
        itemReviewRepository.save(itemReview);

        return new ResponseTemplate(new MyPageResponse.setReview(findUser.getUsername(),request.getRate(),
                request.getReview_content(),itemReviewImgList.stream().map(
                        itemReviewImg -> new MyPageResponse.ImageInfo(itemReviewImg.getImgUrl())).collect(Collectors.toList())
        ));
    }

    @Transactional
    public List<ItemReviewImg> processItemImg(MyPageRequest.setReview request, UtilMethod utilMethod, ItemReview itemReview) {
        List<ItemReviewImg> itemReviewImgList = new ArrayList<>();

        for (int i = 0; i < request.getReview_imgs().size(); i++) {
            if (request.getReview_imgs().get(i).getBase64Data() !=null){
                String url = utilMethod.getImgUrl(request.getReview_imgs().get(i).getBase64Data(),
                        request.getReview_imgs().get(i).getFileName());

                ItemReviewImg itemReviewImg = new ItemReviewImg();
                itemReviewImg.setImgUrl(url);
                itemReviewImg.setItemReview(itemReview);
                itemReviewImgList.add(itemReviewImg);
                itemReviewImgRepository.save(itemReviewImg);
            }
        }

        return itemReviewImgList;
    }

}
