package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;

import java.util.List;

public interface IPermissionService {

    //查詢所有权限
    List<Permission> findAll() throws Exception;

    //新增权限
    void save(Permission permission) throws Exception;
}
