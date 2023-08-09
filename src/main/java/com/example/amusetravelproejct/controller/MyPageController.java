package com.example.amusetravelproejct.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.config.util.UtilMethod;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.MyPageRequest;
import com.example.amusetravelproejct.dto.response.MyPageResponse;
import com.example.amusetravelproejct.service.MyPageService;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {

    private final MyPageService myPageService;

    private final UserService userService;

    private final AmazonS3 amazonS3Client;


    @GetMapping("/like")
    public ResponseTemplate<MyPageResponse.getLikeItems> getLikeItems(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return myPageService.getLikeItems(findUser);
    }

    @PostMapping("/item/{item-id}/review")
    public ResponseTemplate<MyPageResponse.setReview> setReview(@PathVariable("item-id") Long item_id,
                                                                @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                @RequestBody MyPageRequest.setReview request) throws Exception{
        UtilMethod utilMethod = new UtilMethod(amazonS3Client);
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return myPageService.setReview(findUser,item_id,request,utilMethod);
    }

    @GetMapping("/item/review")
    public ResponseTemplate<MyPageResponse.getReview> getReview(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return myPageService.getReview(findUser);
    }
}
