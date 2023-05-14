package com.example.amusetravelproejct.service;

import com.example.amusetravelproejct.dto.request.PushPayInfoReq;
import com.example.amusetravelproejct.dto.response.GetUserCouponRes;

import java.util.List;

public interface TradeService  {
    List<GetUserCouponRes> getUserCoupon(Long userId)throws Exception;

    String pushPayType(String payType)throws Exception;

    String pushPayInfo(PushPayInfoReq pushPayInfoReq) throws Exception;
}
