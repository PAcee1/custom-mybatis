package com.enbuys.sqlSession;

/**
 * @author pace
 * @version v1.0
 * @Type SqlSession.java
 * @Desc
 * @date 2020/4/11 16:42
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
