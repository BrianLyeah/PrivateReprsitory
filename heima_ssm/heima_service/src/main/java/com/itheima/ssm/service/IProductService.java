package com.itheima.ssm.service;

import com.itheima.ssm.domain.Product;
import java.util.List;

public interface IProductService {

    //查询所有产品
    List<Product> findAll(Integer page,Integer pageSize) throws Exception;

    //新增产品信息
    void save(Product product);

    //根据id進行查詢
    Product findById(String id);
}
