package com.itheima.ssm.service;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {

    //根据用户名称查詢用户。自定义service继承UserDetailsService接口，并复写其方法
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    //查詢所有用户得方法
    List<UserInfo> findAll() throws Exception;

    //创建新用户
    void save(UserInfo userInfo) throws Exception;

    //查詢用户详情
    UserInfo findByid(String id) throws Exception;

    //查詢用户其余可添加的角色
    List<Role> findOtherRoles(String userid) throws Exception;

    void addRoleToUser(String userid, String[] roleIds);
}
