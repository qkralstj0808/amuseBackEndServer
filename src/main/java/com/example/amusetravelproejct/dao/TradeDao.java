package com.example.amusetravelproejct.dao;

import com.example.amusetravelproejct.dto.request.PushPayInfoReq;
import com.example.amusetravelproejct.dto.response.GetUserCouponRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TradeDao {
    List<GetUserCouponRes> getUserCoupon(Long userId);

    int pushPayType(String payType);

    int pushPayInfo(PushPayInfoReq pushPayInfoReq);
}
