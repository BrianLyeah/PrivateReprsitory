package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {

    //根据用户名称查詢用户信息
    @Select("select * from users where username=#{username}")
    @Results({@Result(id = true,property = "id",column = "id"),
              @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findByUserId"))
    })
    UserInfo findByUsername(String username);

    //查詢所有用户
    @Select("select * from users")
    List<UserInfo> findAll();

    //创建新用户
    @Insert("insert into users(email,username,password,phoneNum,status) values(#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    //查詢用户详情
    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findByUserId"))
    })
    UserInfo findById(String id);

    //查詢用户可以添加的角色
    @Select("select * from role where id not in (select roleid from users_role where userid=#{userId})")
    List<Role> findOtherRolesByUserid(String userId);

    /**
     * 为用户添加角色
     * @param userid
     * @param roleId
     */
    @Insert("insert into users_role(userid,roleid) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") String userid, @Param("roleId") String roleId);
}
