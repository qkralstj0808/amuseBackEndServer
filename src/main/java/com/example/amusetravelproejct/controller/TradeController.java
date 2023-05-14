package com.example.amusetravelproejct.controller;


import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.PushPayInfoReq;
import com.example.amusetravelproejct.dto.response.GetUserCouponRes;
import com.example.amusetravelproejct.service.TradeService;
import lombok.Getter;
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
}
