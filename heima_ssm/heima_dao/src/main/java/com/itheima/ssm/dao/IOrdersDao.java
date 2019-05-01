package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Member;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IOrdersDao {

    //查詢所有订单的方法
    @Select("select * from orders")
    @Results(id = "id",
            value = {
                    @Result(id = true,property = "id",column = "id"),
                    @Result(property = "product",column = "productid",javaType = Product.class,one = @One(select="com.itheima.ssm.dao.IProductDao.findById"))
            })
    List<Orders> findAll() throws Exception;

    //查詢一个订单的详情
    @Select("select * from orders where id=#{ordersId}")
    @Results({
                    @Result(id=true,property = "id",column = "id"),
                    @Result(property = "product",column = "productId",javaType = Product.class,one=@One(select="com.itheima.ssm.dao.IProductDao.findById")),
                    @Result(property = "member",column = "memberId",javaType = Member.class,one=@One(select="com.itheima.ssm.dao.IMemberDao.findById")),
                    @Result(property = "travellers",column = "id",javaType = java.util.List.class,many=@Many(select="com.itheima.ssm.dao.ITraveller.findByOrdersId"))
            }
    )
    Orders findById(String ordersId) throws Exception;
}
