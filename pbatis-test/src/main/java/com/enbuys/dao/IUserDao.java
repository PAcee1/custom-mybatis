package com.enbuys.dao;

import com.enbuys.pojo.User;

import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type IUserDao.java
 * @Desc
 * @date 2020/4/12 14:34
 */
public interface IUserDao {

    List<User> selectList();

    User selectOne(User user);

}
