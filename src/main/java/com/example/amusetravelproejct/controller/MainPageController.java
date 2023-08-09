package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.domain.person_enum.Grade;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.MainPageService;
import com.example.amusetravelproejct.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class MainPageController {

    private final MainPageService mainPageService;

    private final UserService userService;

    // main page에서 가장 좋아요 수 많은 상품 10개 가지고 오기
    @GetMapping("/main/best-item")
    public ResponseTemplate<?> getBestItem(){
        return mainPageService.getBestItem();
    }

    // main page에서 최근에 만들어진 상품 10개 가지고 오기
    @GetMapping("/main/current-item")
    public ResponseTemplate<?> getCurrentItem(){
        return mainPageService.getCurrentItem();
    }

    // 카테고리별 가장 좋아요 수가 많은 상품 10개 가지고 오기
    @GetMapping("/category/{category-id}/best-item")
    public ResponseTemplate<?> getCategoryBestItem(@PathVariable("category-id") Long category_id){
        return mainPageService.getCategoryBestItem(category_id);
    }

    // 카테고리별 최근에 만들어진 상품 10개 가지고 오기
    @GetMapping("/category/{category-id}/current-item")
    public ResponseTemplate<?> getCategoryCurrentItem(@PathVariable("category-id") Long category_id){
        return mainPageService.getCategoryCurrentItem(category_id);
    }

    // 카테고리별 좋아요 순으로 모두 보기
    @GetMapping("/category/{category-id}/best-item/page={i}")
    public ResponseTemplate<?> getCategoryBestItemAllPage(@PathVariable("category-id") Long category_id,
                                                                             @PathVariable("i") int i){
        int current_page = i-1;
        if(current_page < 0){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }
        PageRequest pageRequest = PageRequest.of(current_page, 40);
        return mainPageService.getCategoryBestItemAllPage(category_id,current_page+1,pageRequest);
    }

    // 카테고리별 좋아요 순으로 모두 보기
    @GetMapping("/category/{category-id}/current-item/page={i}")
    public ResponseTemplate<?> getCategoryCurrentItemAllPage(@PathVariable("category-id") Long category_id,
                                                                             @PathVariable("i") int i){
        int current_page = i-1;
        if(current_page < 0){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }
        PageRequest pageRequest = PageRequest.of(current_page, 40);
        return mainPageService.getCategoryCurrentItemAllPage(category_id,current_page+1,pageRequest);
    }

    @GetMapping("/main-page/lists")
    public ResponseTemplate<MainPageResponse.getListItem> getListItem(){

        return mainPageService.getListItem();
    }

    @GetMapping("/main/category")
    public ResponseTemplate<MainPageResponse.getCategory> getCategory(){
        return mainPageService.getCategory();
    }

    @GetMapping("/main/category/{category-id}/page")
    public ResponseTemplate<MainPageResponse.getCategoryPage> getCategoryPage(
            @PathVariable("category-id") Long category_id,@AuthenticationPrincipal UserPrincipal userPrincipal){
        Grade grade = Grade.BRONZE;
        if(!(userPrincipal == null)){
            User findUser = userService.getUserByPrincipal(userPrincipal);
            grade = findUser.getGrade();
        }
        log.info("grade : " + grade);
        log.info("grade" + grade.ordinal());
        return mainPageService.getCategoryPage(category_id,grade);
    }

    @GetMapping("/main/category/my-item")
    public ResponseTemplate<MainPageResponse.getItem> getMyItem(@AuthenticationPrincipal UserPrincipal userPrincipal){
        User findUser = userService.getUserByPrincipal(userPrincipal);
        return mainPageService.getMyItem(findUser);
    }
}
