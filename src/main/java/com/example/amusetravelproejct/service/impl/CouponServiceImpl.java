package com.example.amusetravelproejct.service.impl;


import com.example.amusetravelproejct.dao.CouponDao;
import com.example.amusetravelproejct.dto.request.InsertCouponTypeReq;
import com.example.amusetravelproejct.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {


    private final CouponDao couponDao;

    @Override
    public String pushCoupon(InsertCouponTypeReq insertCouponTypeReq) throws Exception {

        int val = couponDao.pushCoupon(insertCouponTypeReq);

        if (val == 1)
            return new String("새로운 쿠폰종류가 입력되었습니다.");
        else {
            throw new RuntimeException("쿠폰 삽입에 실패하였습니다.");
        }

    }

}
