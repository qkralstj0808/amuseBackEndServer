package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.CustomException;
import com.example.amusetravelproejct.config.resTemplate.ErrorCode;
import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.response.DetailPageResponse;
import com.example.amusetravelproejct.dto.response.MyPageResponse;
import com.example.amusetravelproejct.service.MyPageService;
import com.example.amusetravelproejct.social.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/like")
    public ResponseTemplate<MyPageResponse.getLikeItems> getLikeItems(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        if(userPrincipal == null){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        String user_id = userPrincipal.getUserId();

        return myPageService.getLikeItems(user_id);
    }
}
