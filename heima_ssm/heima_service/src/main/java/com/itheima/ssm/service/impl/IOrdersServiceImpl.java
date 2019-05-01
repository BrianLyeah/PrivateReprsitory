package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IOrdersDao;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IOrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao iOrdersDao;

    //查詢所有订单
    public List<Orders> findAll(Integer page,Integer pageSize) throws Exception {
        //查詢所有订单
        //使用pageshelpe进行分页.pageNum代表是当前页码值，pageSize是每页条数
        PageHelper.startPage(page,pageSize);
        return iOrdersDao.findAll();
    }

    //查詢一个订单的详情
    public Orders findById(String ordersId) throws Exception {
        return iOrdersDao.findById(ordersId);
    }
}
