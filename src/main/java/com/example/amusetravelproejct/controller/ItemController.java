package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.ItemSearchRequest;
import com.example.amusetravelproejct.dto.response.ItemResponse;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/search/title/page={i}")
    public ResponseTemplate<MainPageResponse.getItemPage> searchItemByWordAndLike_numSortInTitle(@RequestParam("keyword") String keyword,
                                                                                           @PathVariable("i") int i){
        int current_page = i-1;
        if(current_page < 0){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        String[] contain_words = StringUtils.split(keyword);

        for(String contain_word:contain_words){
            log.info(contain_word);
        }

        PageRequest pageRequest = null;

        Sort sort = Sort.by("like_num").descending();
        pageRequest = PageRequest.of(current_page, 60,sort);

        return itemService.searchItemByWordAndConditionSortInTitle(current_page+1,pageRequest,contain_words);
    }

    @GetMapping("/search/content/page={i}")
    public ResponseTemplate<MainPageResponse.getItemPage> searchItemByWordAndLike_numSortInContent(@RequestParam("keyword") String keyword,
                                                                                          @PathVariable("i") int i){
        int current_page = i-1;
        if(current_page < 0){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        String[] contain_words = StringUtils.split(keyword);

        PageRequest pageRequest = null;

        Sort sort = Sort.by("like_num").descending();
        pageRequest = PageRequest.of(current_page, 30,sort);

        return itemService.searchItemByWordAndConditionSortInContent(current_page+1,pageRequest,contain_words);
    }

    @GetMapping("/search/title/condition/page={i}")
    public ResponseTemplate<MainPageResponse.getItemPage> searchItemByWordAndConditionSort(@RequestBody ItemSearchRequest.ItemSearchConditionDto request,
                                                                                        @PathVariable("i") int i){
        int current_page = i-1;
        if(current_page < 0){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        String[] contain_words = StringUtils.split(request.getContain_word());
        List<ItemSearchRequest.SortCondition> sortConditions = request.getSortConditions();

        PageRequest pageRequest = null;

        Sort sort = null;

        if(sortConditions == null){
            sort = Sort.by("like_num").descending();
            pageRequest = PageRequest.of(current_page, 30);
        }else{
            sort = null;
            for(ItemSearchRequest.SortCondition sortCondition : sortConditions){
                if(sort == null){       // 첫번째 sort 조건일 경우
                    Sort firstSort = firstCondition(sortCondition);
                    if(firstSort == null){
                        throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
                    }else{
                        sort = firstSort;
                    }
                }else{
                    Sort afterFirstSort = afterFirstCondition(sort, sortCondition);
                    if(afterFirstSort == null){
                        throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
                    }else{
                        sort = afterFirstSort;
                    }
                }
            }
        }
        pageRequest = PageRequest.of(current_page, 30,sort);

        return itemService.searchItemByWordAndConditionSortInTitle(current_page+1,pageRequest,contain_words);
    }

    Sort firstCondition(ItemSearchRequest.SortCondition sortCondition){
        if(sortCondition.getRated()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return Sort.by("rated").ascending();
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return Sort.by("rated").descending();
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }else if(sortCondition.getLike_num()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return Sort.by("like_num").ascending();
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return Sort.by("like_num").descending();
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }else if(sortCondition.getStartPrice()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return Sort.by("startPrice").ascending();
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return Sort.by("startPrice").descending();
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }else if(sortCondition.getDate()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return Sort.by("date").ascending();
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return Sort.by("date").descending();
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }
        return null;
    }

    Sort afterFirstCondition(Sort sort, ItemSearchRequest.SortCondition sortCondition){
        if(sortCondition.getRated()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return sort.and(Sort.by("rated").ascending());
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return sort.and(Sort.by("rated").descending());
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }else if(sortCondition.getLike_num()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return sort.and(Sort.by("like_num").ascending());
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return sort.and(Sort.by("like_num").descending());
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }else if(sortCondition.getStartPrice()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return sort.and(Sort.by("startPrice").ascending());
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return sort.and(Sort.by("startPrice").descending());
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }else if(sortCondition.getDate()){
            if(sortCondition.getAsc_or_desc().equals("오름차순")){
                return sort.and(Sort.by("date").ascending());
            }else if(sortCondition.getAsc_or_desc().equals("내림차순")){
                return sort.and(Sort.by("date").descending());
            }else{
                throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
            }
        }
        return null;
    }

    @GetMapping("/item/all")
    public ResponseTemplate<ItemResponse.getAllItemId> getAllItemId(){
        return itemService.getAllItemId();
    }

}
