package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.response.UserResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.UserService;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("")
//    public ResponseTemplate<String> getUser() {
////        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return new ResponseTemplate("과정만 보자");
//    }

    @GetMapping("/info")
    public ResponseTemplate<UserResponse.getUserInfo> getAdminInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return new ResponseTemplate(new UserResponse.getUserInfo(findUser.getUserId(),findUser.getUsername(),
                findUser.getProfileImageUrl(),findUser.getEmail(),findUser.getGrade()));
    }


}
