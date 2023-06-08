package com.example.amusetravelproejct.service.impl;

import com.example.amusetravelproejct.dao.TradeDao;
import com.example.amusetravelproejct.dto.request.PushPayInfoReq;
import com.example.amusetravelproejct.dto.response.GetUserCouponRes;
import com.example.amusetravelproejct.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {

    private final TradeDao tradeDao;

    @Override
    public List<GetUserCouponRes> getUserCoupon(Long userId) throws Exception {
        List<GetUserCouponRes> result = tradeDao.getUserCoupon(userId);
        return result;
    }

    @Override
    public String pushPayType(String payType) throws Exception {
        int val = tradeDao.pushPayType(payType);
        if(val == 1)
            return new String("결제방법이 추가되었습니다.");
        else
            throw new Exception("결제방법 추가에 실패하였습니다.");
    }

    @Override
    public String pushPayInfo(PushPayInfoReq pushPayInfoReq) throws Exception {
        int val = tradeDao.pushPayInfo(pushPayInfoReq);
        if(val == 1)
            return new String("구매정보가 등록되었습니다.");
        else
            throw new Exception("구매정보 등록에 실패했습니다.");

    }

    @Override
    public String getRefund(Long payInfoId) throws Exception {
        tradeDao.getRefund(payInfoId);

        return new String("환불 처리가 완료되었습니다.");
    }
}
