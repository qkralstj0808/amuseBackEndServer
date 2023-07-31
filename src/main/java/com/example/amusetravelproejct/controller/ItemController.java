package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.response.ItemResponse;
import com.example.amusetravelproejct.dto.response.MainPageResponse;
import com.example.amusetravelproejct.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    // ex) /search/title/condition?keyword=여수&sort=rated_asc,like_num_desc,like_num_asc&page=1
    // keyword가 없어도 되고 sort가 없어도 됩니다
    // 제목에 여수가 들어있는 모든 상품을 평점 높은 순으로, 만약에 평점 같으면 좋아요 낮은것부터 정렬되서 보여준다.
    @GetMapping("/search")
    public ResponseTemplate<MainPageResponse.getItemPage> searchItemByWordAndConditionSort(
            @RequestParam("page") Integer i,
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "sort",required = false) String list_conditions
    ){
        int current_page = i-1;
        if(current_page < 0){
            throw new CustomException(ErrorCode.OUT_BOUND_PAGE);
        }

        String[] contain_words;

        if(keyword != null){
            contain_words = StringUtils.split(keyword);
        }else{
            contain_words = null;
        }

        String[] conditions;
        log.info("query_conditions : " + list_conditions);

        // condition에는 총 8가지 가능하다.
        // rated_asc, rated_desc
        // date_asc, date_desc
        // startPrice_asc, startPrice_desc
        // like_num_asc, like_num_desc
        // 정렬에는 순서도 중요하다 따라서 conditions배열에 있는 정렬 순서대로 정렬되도록 한다.
        if(list_conditions != null){
            conditions = StringUtils.split(list_conditions,",");
        }else{
            conditions = null;
        }

        PageRequest pageRequest = null;

        Sort sort = null;

        if(conditions == null){
            sort = Sort.by("like_num").descending();
            pageRequest = PageRequest.of(current_page, 30);
        }else{
            sort = null;
            for(String sort_condition : conditions){
                if(sort == null){       // 첫번째 sort 조건일 경우
                    Sort firstSort = changeConditionBySort(sort_condition);
                    if(firstSort == null){
                        throw new CustomException(ErrorCode.ASC_DESC_WORD_ERROR);
                    }else{
                        sort = firstSort;
                    }
                }else{
                    sort = sort.and(changeConditionBySort(sort_condition));
//                            afterFirstCondition(sort, sortCondition);
                }
            }
        }

        pageRequest = PageRequest.of(current_page, 10,sort);

        return itemService.searchItemByWordAndConditionSort(current_page+1,pageRequest,contain_words);
    }

    Sort changeConditionBySort(String sortCondition){

        if(sortCondition.equals("rated_asc")) {
            return Sort.by("rated").ascending();
        }else if(sortCondition.equals("rated_desc")) {
            return Sort.by("rated").descending();
        }else if(sortCondition.equals("date_asc")) {
            return Sort.by("date").ascending();
        }else if(sortCondition.equals("date_desc")) {
            return Sort.by("date").descending();
        }else if(sortCondition.equals("like_num_asc")) {
            return Sort.by("like_num").ascending();
        }else if(sortCondition.equals("like_num_desc")) {
            return Sort.by("like_num").descending();
        }else if(sortCondition.equals("startPrice_asc")) {
            return Sort.by("startPrice").ascending();
        }else if(sortCondition.equals("startPrice_desc")) {
            return Sort.by("startPrice").descending();
        }else {
            return null;
        }
    }


    @GetMapping("/all")
    public ResponseTemplate<ItemResponse.getAllItemId> getAllItemId(){
        return itemService.getAllItemId();
    }

}
