package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.Admin;
import com.example.amusetravelproejct.dto.response.AdminResponse;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.AdminService;
import com.example.amusetravelproejct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;


    @GetMapping("/info")
    public String getAdminInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception{
        Admin findAdmin = adminService.getAdminPrincipal(userPrincipal);

        return "id : " + findAdmin.getAdminId();
    }

    @GetMapping("accounts/all")
    public ResponseTemplate<AdminResponse.getAllAccountsId> getAllAccountsId(){
        return adminService.getAllAccountsId();
    }


}
