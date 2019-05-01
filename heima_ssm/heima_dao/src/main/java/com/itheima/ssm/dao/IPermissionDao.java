package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPermissionDao {


    //通过角色名称查詢权限
    @Select("select * from permission where id in (select permissionid from role_permission where roleid=#{id})")
    List<Permission> findPermissionByRoleId(String id) throws Exception;

    //查詢所有权限信息
    @Select("select * from permission")
    List<Permission> findAll();


    //新增权限
    @Insert("insert into permission(permissionName,url) values(#{permissionName},#{url})")
    void save(Permission permission);
}
