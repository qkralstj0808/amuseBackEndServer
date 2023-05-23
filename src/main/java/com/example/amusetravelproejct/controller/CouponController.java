package com.example.amusetravelproejct.controller;


import com.example.amusetravelproejct.config.resTemplate.ResponseTemplate;
import com.example.amusetravelproejct.dto.request.InsertCouponTypeReq;
import com.example.amusetravelproejct.dto.response.GetCouponRes;
import com.example.amusetravelproejct.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;


    @Operation(summary = "쿠폰종류 등록", description = "쿠폰종류 등록하는 API입니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "생성 실패",
                    content=@Content(schema=@Schema(implementation =ResponseTemplate.class))),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류" ,
                    content=@Content(schema=@Schema(implementation = ResponseTemplate.class)))
    })
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


    @Operation(summary = "쿠폰종류 조회", description = "쿠폰종류 조회하는 API입니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "생성 실패",
                    content=@Content(schema=@Schema(implementation =ResponseTemplate.class))),
            @ApiResponse(responseCode = "500",description = "서버 내부 오류" ,
                    content=@Content(schema=@Schema(implementation = ResponseTemplate.class)))
    })
    @GetMapping("/getCoupon/{couponCode}")
    public ResponseTemplate<GetCouponRes> getCoupon(@PathVariable String couponCode){
        try{
            GetCouponRes result = couponService.getCoupon(couponCode);
            return new ResponseTemplate<>(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseTemplate<>(new GetCouponRes());
        }
    }
}
