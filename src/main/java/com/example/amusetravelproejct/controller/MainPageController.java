package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/main/category")
    public ResponseTemplate<MainPageResponse.getCategory> getCategory(){
        return mainPageService.getCategory();
    }

    // main page에서 가장 좋아요 수 많은 상품 10개 가지고 오기
    @GetMapping("/main/best-item")
    public ResponseTemplate<MainPageResponse.getBestItem> getBestItem(){
        return mainPageService.getBestItem();
    }

    // main page에서 최근에 만들어진 상품 10개 가지고 오기
    @GetMapping("/main/current-item")
    public ResponseTemplate<MainPageResponse.getCurrentItem> getCurrentItem(){
        return mainPageService.getCurrentItem();
    }

    // 카테고리별 가장 좋아요 수가 많은 상품 10개 가지고 오기
    @GetMapping("/category/{category-id}/best-item")
    public ResponseTemplate<MainPageResponse.getBestItem> getCategoryBestItem(@PathVariable("category-id") Long category_id){
        return mainPageService.getCategoryBestItem(category_id);
    }

    // 카테고리별 최근에 만들어진 상품 10개 가지고 오기
    @GetMapping("/category/{category-id}/current-item")
    public ResponseTemplate<MainPageResponse.getCurrentItem> getCategoryCurrentItem(@PathVariable("category-id") Long category_id){
        return mainPageService.getCategoryCurrentItem(category_id);
    }

    // 카테고리별 모든 상품 보기
    @GetMapping("/category/{category-id}")
    public ResponseTemplate<MainPageResponse.getItem> getCategoryAllItem(@PathVariable("category-id") Long category_id){
        return mainPageService.getCategoryAllItem(category_id);
    }

    // 카테고리별 모든 상품 보기
    @GetMapping("/province/{category-id}")
    public ResponseTemplate<MainPageResponse.getItem> getCityAllItem(@PathVariable("category-id") Long category_id){
        return mainPageService.getCategoryAllItem(category_id);
    }
}
