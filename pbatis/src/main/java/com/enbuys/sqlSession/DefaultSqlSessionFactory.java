package com.enbuys.sqlSession;

import com.enbuys.pojo.Configuration;

/**
 * @author pace
 * @version v1.0
 * @Type DefaultSqlSessionFactory.java
 * @Desc
 * @date 2020/4/11 17:48
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    // Configuration需要向下传递，所以需要创建成员变量
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
