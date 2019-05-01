package com.itheima.ssm.service;

import com.itheima.ssm.domain.Role;

import java.util.List;

public interface IRoleService {

    //查詢所有角色信息
    List<Role> findAll() throws Exception;

    //新增角色信息
    void save(Role role) throws Exception;
}
