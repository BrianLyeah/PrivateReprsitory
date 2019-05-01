package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IUserDao;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class IUserServiceImpl implements IUserService {

    @Autowired
    private IUserDao iUserDao;


    //自动注入密码加密的对象
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //获取springSequrity中的UserDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //根据用户名称，查詢数据库中的UserInfo对象
        UserInfo userInfo=iUserDao.findByUsername(username);
        System.out.println(userInfo);
        //创建sequrity定义的User对象，将查詢出来的结果赋予给他，并将其返回
        User user= null;
        try {
            user = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()==0?false:true,true,true,true,getAuthority(userInfo.getRoles()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    //定义一个获取权限集合的方法
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roleList)throws Exception{
        //创建一个SimpleGrantedAuthority类型的集合,参数为String类型的role
        List<SimpleGrantedAuthority> list=new ArrayList<SimpleGrantedAuthority>();
        //遍历参数参数，获取其中的角色名.并将其拼接上一个"ROLE_"后，存入list集合中
        for (Role role : roleList) {
            SimpleGrantedAuthority role_user = new SimpleGrantedAuthority("ROLE_"+role.getRoleName());
            list.add(role_user);
        }

        return list;
    }


    //查詢所有用户
    @Override
    public List<UserInfo> findAll() throws Exception{
        return iUserDao.findAll();

    }

    //创建新用户
    @Override
    public void save(UserInfo userInfo) throws Exception{
        //取出用户的密码进行加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        iUserDao.save(userInfo);
    }

    //查詢用户详情
    @Override
    public UserInfo findByid(String id) throws Exception{
        return iUserDao.findById(id);
    }

    //根据用户id查詢他可以添加的角色信息
    @Override
    public List<Role> findOtherRoles(String userid) throws Exception {
        return iUserDao.findOtherRolesByUserid(userid);

    }

    /**
     * 为用户添加权限
     * @param userid
     * @param roleIds
     */
    @Override
    public void addRoleToUser(String userid, String[] roleIds) {
        //遍历权限，调用dao层进行添加
        for (String roleId : roleIds) {
            iUserDao.addRoleToUser(userid,roleId);
        }
    }
}
