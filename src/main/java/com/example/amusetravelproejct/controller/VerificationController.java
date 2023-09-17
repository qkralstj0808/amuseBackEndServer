package com.example.amusetravelproejct.controller;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.EmailRequest;
import com.example.amusetravelproejct.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/verification")
@RequiredArgsConstructor
@Slf4j
public class VerificationController {

    private final EmailService emailService;

    // 이메일 인증 요청 버튼에 사용
    @PostMapping("/email/request")
    @ResponseBody
    public ResponseTemplate<String> mailConfirm(@RequestBody EmailRequest.Email request) throws Exception {
        String code = emailService.sendSimpleMessage(request.getEmail());
        log.info("인증코드 : " + code);
        return new ResponseTemplate<>(code);
    }
}
