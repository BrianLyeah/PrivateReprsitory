package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IProductDao;
import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao iProductDao;

    //查询所有产品信息
    public List<Product> findAll(Integer page,Integer pageSize) throws Exception {

       PageHelper.startPage(page,pageSize);
       return iProductDao.findAll();
    }

    //新增产品信息
    public void save(Product product) {
        iProductDao.save(product);

    }

    public Product findById(String id) {
        return iProductDao.findById(id);
    }
}
