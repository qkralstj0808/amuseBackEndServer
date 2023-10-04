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

    @GetMapping("/info")
    public ResponseTemplate<UserResponse.getUserInfo> getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal){
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
                                                                     @RequestBody UserRequest.createUserInfoBeforeLogin request){
        User findUser = userService.getUserByPrincipal(userPrincipal);

        return userService.createUserInfoBeforeLogin(findUser,request);
    }

    @DeleteMapping("/withdraw")
    public ResponseTemplate<String> withdrawSocialLogin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userService.getUserByPrincipal(userPrincipal);
        userService.withdrawReservation(user);
        return new ResponseTemplate<>("삭제 성공");
    }

    @GetMapping("/search/id")
    public ResponseTemplate<UserResponse.searchId> searchId(@RequestBody UserRequest.searchId request){
        return userService.searchId(request);
    }

    @GetMapping("/search/password")
    public ResponseTemplate<String> searchPassword(@RequestBody UserRequest.searchPassword request){
        return userService.searchPassword(request);
    }

}
