package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //根据用户名查找用户
    User findByUsername(String userName);
    //注册用户
    boolean save(User user);
   // 激活用户
   boolean activeUser(String code);
}
