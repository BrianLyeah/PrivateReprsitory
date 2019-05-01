package com.itheima.ssm.controller;


import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    //调用dao层
    @Autowired
    private IUserService iUserService;



    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        //调用service层
        List<UserInfo> userList = iUserService.findAll();
        for (UserInfo userInfo : userList) {
            System.out.println(userInfo);
        }
        //将返回的集合存入mv
        mv.addObject("userList",userList);
        mv.setViewName("user-list");
        return mv;
    }

    //新增用户
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/save.do")
    public String  save(UserInfo userInfo) throws Exception {
        iUserService.save(userInfo);
        return "redirect:findAll.do";
    }

    //查詢用户详情
    @RequestMapping("/findById.do")
    public ModelAndView findById(@Param("id") String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo user=iUserService.findByid(id);
        mv.addObject("user",user);
        mv.setViewName("user-show");
        return mv;

    }

    /**
     * 查找用户可以添加的角色
     * @param userid
     * @return
     * @throws Exception
     */
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name="id") String userid) throws Exception {
        ModelAndView mv = new ModelAndView();
        //1.根据用户id查询用户
        UserInfo userInfo = iUserService.findByid(userid);
        //2.根据用户id查询可以添加的角色
        List<Role> otherRoles = iUserService.findOtherRoles(userid);
        mv.addObject("user", userInfo);
        mv.addObject("roleList", otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    /**
     * 为用户添加角色
     * @param userId
     * @param roleIds
     * @return
     */
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId") String userId,@RequestParam(name = "ids") String[] roleIds){
        iUserService.addRoleToUser(userId,roleIds);
        return "redirect:findAll.do";
    }

}
