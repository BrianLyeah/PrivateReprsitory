package com.itheima.ssm.service;

import com.itheima.ssm.domain.Orders;

import java.util.List;

public interface IOrdersService {
    //查詢所有订单
    List<Orders> findAll(Integer page,Integer pageSize) throws Exception;

    //查詢一个订单详情
    Orders findById(String ordersId) throws Exception;

}
