package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    //根据id进行查詢
    @RequestMapping("/findById.do")
    public Product findById(String id){
        return iProductService.findById(id);
    }


    //查询所有
    @RolesAllowed("ADMIN")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(Integer page,Integer pageSize) throws Exception {
        //查詢所有
        List<Product> products = iProductService.findAll(page,pageSize);
        //将查詢集合封装进PageInfo
        PageInfo pageInfo=new PageInfo(products);
        //创建ModelAndView对象
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("product-list");
        return mv;
    }

    //新增产品
    @RequestMapping("/save.do")
    public String save(Product product)throws Exception{
        //获取客户端传递过来的表单对象
        iProductService.save(product);
        //重定向到所有产品界面
        return "redirect:findAll.do";
    }
}
