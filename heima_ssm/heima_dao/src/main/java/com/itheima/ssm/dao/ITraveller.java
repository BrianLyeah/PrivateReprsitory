package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Traveller;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ITraveller {

    //查詢游客信息
    @Select("select * from traveller where id in (select travellerid from ORDER_TRAVELLER where orderid =#{ordersId})")
    List<Traveller> findByOrdersId(String ordersId);
}
