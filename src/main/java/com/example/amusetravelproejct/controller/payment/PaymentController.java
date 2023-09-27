package com.example.amusetravelproejct.controller.payment;

import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.domain.User;
import com.example.amusetravelproejct.dto.request.payment.PaymentCompleteRequestDto;
import com.example.amusetravelproejct.dto.request.payment.PaymentPageRequestDto;
import com.example.amusetravelproejct.dto.response.payment.PaymentPageResponseDto;
import com.example.amusetravelproejct.oauth.entity.UserPrincipal;
import com.example.amusetravelproejct.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {
    private final UserService userService;


    @GetMapping("")
    public ResponseTemplate<PaymentPageResponseDto> getPaymentPage(@AuthenticationPrincipal UserPrincipal userPrincipal)
    {
        User findUser = userService.getUserByPrincipal(userPrincipal);

        String username = findUser.getUsername();
        String phoneNumber = findUser.getPhoneNumber();
        String email = findUser.getEmail();
        Integer point = findUser.getPoint();

        PaymentPageResponseDto paymentPageResponseDto = PaymentPageResponseDto.builder()
                .userName(username)
                .userPhoneNumber(phoneNumber)
                .userEmail(email)
                .userPoint(point)
                .build();

        ResponseTemplate<PaymentPageResponseDto> response = ResponseTemplate.<PaymentPageResponseDto>builder()
                .isSuccess(true)
                .message("Success")
                .code(200)
                .data(paymentPageResponseDto)
                .build();

        return  response;
    }

//    @PostMapping("")
//    public ResponseTemplate<PaymentPageResponseDto> savePayment(@AuthenticationPrincipal UserPrincipal userPrincipal,
//                                                                @RequestBody PaymentCompleteRequestDto paymentCompleteRequestDto
//    )
//    {
//
//    }
}
