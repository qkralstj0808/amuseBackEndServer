package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.AdminRequest;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/search/users")
    public ResponseTemplate<AdminResponse.getUser> getUserInfo(@RequestParam(value = "email",required = true) String email){
        return userService.getUserByEmail(email);
    }

    @PostMapping("/update-role/{user-db-id}")
    public ResponseTemplate<AdminResponse.updateUserRoleType> updateUserRoleType(
            @PathVariable("user-db-id") Long user_db_id,
            @RequestBody AdminRequest.updateUserRoleType request){

        return userService.updateUserRoleType(user_db_id,request);
    }

}
