package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    @Override
    public boolean regist(User user) {
        //1判断是否注册过
        UserDao userDao = new UserDaoImpl();
        User byUsername = userDao.findByUsername(user.getUsername());
        //2注册
        if(byUsername == null){
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());
            boolean save = userDao.save(user);
            //发送邮件
            if(save){
                String text = "<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>激活用户</a>";
                System.out.println(text);
                MailUtils.sendMail(user.getEmail(),text,"激活用户");
            }
            return save;
        }
        return false;
    }

    @Override
    public boolean activeCode(String code) {
        UserDao userDao = new UserDaoImpl();
        return userDao.activeUser(code);
    }
}
