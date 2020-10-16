package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String userName) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName);
        } catch (DataAccessException e) {
            return user;
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
        try {
            int update = template.update(sql, user.getUsername(), user.getPassword(),
                    user.getName(), user.getBirthday(),
                    user.getSex(), user.getTelephone(),
                    user.getEmail(),user.getStatus(),user.getCode());
            if(update >0){
                return true;
            }
        } catch (DataAccessException e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean activeUser(String code) {
        String sql = "update tab_user set status='Y' where code = ?";
        int update = template.update(sql, code);
        if(update >0){
            return true;
        }
        return false;
    }
}
