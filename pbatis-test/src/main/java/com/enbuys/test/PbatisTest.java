package com.enbuys.test;

import com.enbuys.dao.IUserDao;
import com.enbuys.io.Resources;
import com.enbuys.pojo.User;
import com.enbuys.sqlSession.SqlSession;
import com.enbuys.sqlSession.SqlSessionFactory;
import com.enbuys.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.io.InputStream;
import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type PbatisTest.java
 * @Desc
 * @date 2020/4/11 23:12
 */
public class PbatisTest {
    public static void main(String[] args) {
        String configPath = "sqlMapConfig.xml";
        Resources resources = new Resources();
        InputStream inputStream = resources.getResourceAsStream(configPath);
        try {
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession sqlSession = build.openSession();

            // 查询单条数据
            User user = new User();
            user.setId(1);
            user.setUsername("pace");
            /*Object o = sqlSession.selectOne("user.selectOne", user);
            System.out.println(o);

            // 查询全部数据
            List<User> list = sqlSession.selectList("user.selectList");
            System.out.println(list);*/

            // 使用代理类
            IUserDao userDao = sqlSession.getMapper(IUserDao.class);
            User one = userDao.selectOne(user);
            System.out.println(one);

            List<User> users = userDao.selectList();
            System.out.println(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
