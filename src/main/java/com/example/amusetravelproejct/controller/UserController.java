package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.UserRequest;
import com.example.amusetravelproejct.dto.response.UserResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/info")
    public ResponseTemplate<UserResponse.getUserInfo> createUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                     @RequestBody UserRequest.createUserInfo request) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return userService.createUserInfo(findUser,request);


    }

    @GetMapping("/info")
    public ResponseTemplate<UserResponse.getUserInfo> getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return new ResponseTemplate(new UserResponse.getUserInfoBeforeLogin(
                findUser.getUserId(),
                findUser.getUsername(),
                findUser.getProfileImageUrl(),
                findUser.getEmail(),
                findUser.getGrade(),
                findUser.getPhoneNumber(),
                findUser.getAdvertisementTrue(),
                findUser.getOver14AgeTrue()));
    }

    @GetMapping("/all/info")
    public ResponseTemplate<UserResponse.getAllUserInfo> getAllUserInfo(){
        return userService.getAllUserInfo();
    }

    @PostMapping("/{user-db-id}/change/grade")
    public ResponseTemplate<UserResponse.getUserInfo> changeUserGrade(@PathVariable("user-db-id") Long user_db_id,
                                                                      @RequestBody UserRequest.changeUserGrade request){
        return userService.changeUserGrade(user_db_id,request);
    }


    @PostMapping("/login/info")
    public ResponseTemplate<UserResponse.getUserInfoBeforeLogin> createUserInfoBeforeLogin(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                     @RequestBody UserRequest.createUserInfoBeforeLogin request) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return userService.createUserInfoBeforeLogin(findUser,request);
    }

    @GetMapping("/login/info")
    public ResponseTemplate<UserResponse.getUserInfoBeforeLogin> getUserInfoBeforeLogin(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return userService.getUserInfoBeforeLogin(findUser);
    }

}
