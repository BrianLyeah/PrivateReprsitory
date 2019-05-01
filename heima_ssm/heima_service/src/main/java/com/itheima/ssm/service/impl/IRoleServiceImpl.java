package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IRoleDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IRoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao iRoleDao;

    //查詢所有用户信息
    @Override
    public List<Role> findAll() throws Exception{
        return iRoleDao.findAll();

    }

    //新增角色信息
    @Override
    public void save(Role role) throws Exception{
        iRoleDao.save(role);
    }
}
