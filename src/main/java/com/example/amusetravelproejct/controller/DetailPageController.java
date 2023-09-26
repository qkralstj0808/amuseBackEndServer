package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseException;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Item;
import com.example.amusetravelproejct.dto.response.DetailPageResponse;
import com.example.amusetravelproejct.repository.ItemRepository;
import com.example.amusetravelproejct.service.DetailPageService;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
//@Api(tags = {"Forum Api"})
@RequestMapping("/detail/{id}")
public class DetailPageController {

    private final DetailPageService detailPageService;
    private final ItemRepository itemRepository;

    @GetMapping("/title")
    public ResponseTemplate<DetailPageResponse.getTitle> getTitle(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getTitle(item_id);

    }

    @GetMapping("/picture")
    public ResponseTemplate<DetailPageResponse.getPicture> getPicture(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getPicture(item_id);
    }

    @GetMapping("/ticket-select")
    public ResponseTemplate<DetailPageResponse.getTicket> getTicket(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getTicket(item_id);
    }

    @GetMapping("/product-intro")
    public ResponseTemplate<DetailPageResponse.getContent> getContent(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getContent(item_id);
    }

    @GetMapping("/course-intro")
    public ResponseTemplate<DetailPageResponse.getCourseContent> getCourseContent(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getCourseContent(item_id);
    }

    @GetMapping("/other-info")
    public ResponseTemplate<DetailPageResponse.getOtherContent> getOtherContent(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getOtherContent(item_id);
    }

    @GetMapping("/guide-info")
    public ResponseTemplate<DetailPageResponse.getManager> getManager(@PathVariable("id")  Long item_id) throws IOException, ResponseException {
        return detailPageService.getManager(item_id);
    }

    @PostMapping("/like-plus")
    public ResponseTemplate<DetailPageResponse.setLike> setLikePlus(@PathVariable("id") Long item_id,
                                                                @AuthenticationPrincipal UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String user_id = userPrincipal.getUserId();
        return detailPageService.setLikePlus(item_id,user_id);
    }

    @PostMapping("/like-minus")
    public ResponseTemplate<DetailPageResponse.setLike> setLikeMinus(@PathVariable("id") Long item_id,
                                                                @AuthenticationPrincipal UserPrincipal userPrincipal){
        if(userPrincipal == null){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String user_id = userPrincipal.getUserId();
        return detailPageService.setLikeMinus(item_id,user_id);
    }

    @GetMapping("/review")
    public ResponseTemplate<DetailPageResponse.getReview> getReview(@PathVariable("id") Long item_id){
        Item item = itemRepository.findById(item_id).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        ResponseTemplate<DetailPageResponse.getReview> review = detailPageService.getReview(item);
        return review;
    }

}
