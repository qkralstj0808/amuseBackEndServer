package com.example.amusetravelproejct.controller;


import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.InsertCouponTypeReq;
import com.example.amusetravelproejct.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/push")
    public ResponseTemplate<String> pushCoupon(@RequestBody InsertCouponTypeReq insertCouponTypeReq){
        try {
            String result = couponService.pushCoupon(insertCouponTypeReq);
            return new ResponseTemplate<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseTemplate<>(new String(e.getMessage()));
        }


    }
}
