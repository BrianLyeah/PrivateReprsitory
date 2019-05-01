package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    //创建一个ModelAndView对象
    ModelAndView mv = new ModelAndView();

    @Autowired
    private IOrdersService iOrdersService;

    @Secured("ROLE_ADMIN")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize) throws Exception{
        System.out.println(page);
        System.out.println(pageSize);
        //创建一个mv对象
        mv = new ModelAndView();
        //调用service层的方法
        List<Orders> ordersList=iOrdersService.findAll(page,pageSize);
        //
        PageInfo pageInfo = new PageInfo(ordersList);
        //将查詢结果存入mv中
        mv.addObject("pageInfo",pageInfo);
        //设置返回的界面
        mv.setViewName("orders-list");
        return mv;
    }

    //查詢一个订单详情
    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam("id") String ordersId) throws Exception{
        //创建一个
        mv=new ModelAndView();
        //调用service层进行订单详情
        Orders orders=iOrdersService.findById(ordersId);
        System.out.println(orders);
        //将返回的对象存储金mv
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
        return mv;
    }
}

