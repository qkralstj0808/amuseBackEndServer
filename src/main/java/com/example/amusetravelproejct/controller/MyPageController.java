package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.MyPageRequest;
import com.example.amusetravelproejct.dto.response.MyPageResponse;
import com.example.amusetravelproejct.repository.UserRepository;
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

    @GetMapping("/like")
    public ResponseTemplate<MyPageResponse.getLikeItems> getLikeItems(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return myPageService.getLikeItems(findUser);
    }

    @PostMapping("/review")
    public ResponseTemplate<MyPageResponse.setReview> setReview(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                @RequestBody MyPageRequest.setReview request) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return myPageService.setReview(findUser,request);
    }
}
