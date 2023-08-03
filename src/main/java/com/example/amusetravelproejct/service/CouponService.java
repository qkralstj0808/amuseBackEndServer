package com.example.amusetravelproejct.service;


import com.example.amusetravelproejct.dto.request.InsertCouponTypeReq;
import com.example.amusetravelproejct.dto.response.GetCouponRes;


public interface CouponService {


    String pushCoupon(InsertCouponTypeReq insertCouponTypeReq)throws Exception;

    GetCouponRes getCoupon(String couponCode) throws Exception;
}
