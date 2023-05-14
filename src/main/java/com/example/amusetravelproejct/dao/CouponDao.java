package com.example.amusetravelproejct.dao;

import com.example.amusetravelproejct.dto.request.InsertCouponTypeReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponDao {

    int pushCoupon(InsertCouponTypeReq insertCouponTypeReq);


}
