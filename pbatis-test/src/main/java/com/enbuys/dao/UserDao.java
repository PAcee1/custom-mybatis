package com.enbuys.dao;

import com.enbuys.io.Resources;
import com.enbuys.pojo.User;
import com.enbuys.sqlSession.SqlSession;
import com.enbuys.sqlSession.SqlSessionFactory;
import com.enbuys.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type UserDao.java
 * @Desc
 * @date 2020/4/12 14:34
 */
public class UserDao implements IUserDao {
    public List<User> selectList() {
        String configPath = "sqlMapConfig.xml";
        Resources resources = new Resources();
        InputStream inputStream = resources.getResourceAsStream(configPath);
        try {
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = build.openSession();

            // 查询全部数据
            List<User> list = sqlSession.selectList("user.selectList");
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User selectOne(User user) {
        String configPath = "sqlMapConfig.xml";
        Resources resources = new Resources();
        InputStream inputStream = resources.getResourceAsStream(configPath);
        try {
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = build.openSession();
            Object o = sqlSession.selectOne("user.selectOne", user);
            return (User) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
