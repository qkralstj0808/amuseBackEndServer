package com.example.amusetravelproejct.controller;


import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.PushPayInfoReq;
import com.example.amusetravelproejct.dto.response.GetUserCouponRes;
import com.example.amusetravelproejct.service.EmailService;
import com.example.amusetravelproejct.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    private final EmailService emailService;


    @Operation(summary = "유저 쿠폰 조회", description = "유저 쿠폰 조회하는 API입니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "생성 실패",
                    content=@Content(schema=@Schema(implementation =ResponseTemplate.class))),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류" ,
                    content=@Content(schema=@Schema(implementation = ResponseTemplate.class)))
    })
    @GetMapping("/userCoupon/{userId}")
    public ResponseTemplate<List<GetUserCouponRes>> getUserCoupon(@PathVariable Long userId){
        try{
            List<GetUserCouponRes> result = tradeService.getUserCoupon(userId);
            for(GetUserCouponRes item : result){
                if(item.getDiscountType() == 1)
                    item.setTypeOrigin(new String("-"));
                else if (item.getDiscountType() == 2) {
                    item.setTypeOrigin(new String("%"));
                }
            }
            return new ResponseTemplate<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseTemplate<>(new ArrayList<>());
        }
    }


    @Operation(summary = "결제타입 등록", description = "결제타입(카드사,토스..)등록하는 API입니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "생성 실패",
                    content=@Content(schema=@Schema(implementation =ResponseTemplate.class))),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류" ,
                    content=@Content(schema=@Schema(implementation = ResponseTemplate.class)))
    })
    @PostMapping("/payType")
    public ResponseTemplate<String> pushPayType(@RequestParam String method){
        try {
            String result = tradeService.pushPayType(method);
            return new ResponseTemplate<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseTemplate<>("");
        }
    }


    @Operation(summary = "결제정보 등록", description = "결제정보 등록하는 API입니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "생성 실패",
                    content=@Content(schema=@Schema(implementation =ResponseTemplate.class))),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류" ,
                    content=@Content(schema=@Schema(implementation = ResponseTemplate.class)))
    })
    @PostMapping("/pushPayInfo")
    public ResponseTemplate<String> pushPayInfo(@RequestBody PushPayInfoReq pushPayInfoReq){
        try {
            String result = tradeService.pushPayInfo(pushPayInfoReq);
            return new ResponseTemplate<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseTemplate<>("");
        }
    }

    @PostMapping("login/mailConfirm")
    @ResponseBody
    public ResponseTemplate<String> mailConfirm(@RequestParam String email) throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return new ResponseTemplate<>(code);
    }

    @PatchMapping("/refund")
    public ResponseTemplate<String> getRefund(@PathVariable Long payInfoId){
        try{
            String result = tradeService.getRefund(payInfoId);
            return new ResponseTemplate<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseTemplate<>(new String());
        }
    }
}
