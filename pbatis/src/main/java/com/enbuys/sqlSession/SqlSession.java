package com.enbuys.sqlSession;

import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type SqlSession.java
 * @Desc
 * @date 2020/4/11 17:52
 */
public interface SqlSession {
    // 这里我们只编写两种情况，其他情况其实代码都是类似的

    /**
     * 查询List，需要传入id和参数们
     * @param statementId
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String statementId,Object... objects) throws Exception;

    /**
     * 查询单条记录，需要传入id和参数们
     * @param statementId
     * @param objects
     * @param <E>
     * @return
     */
    <E> E selectOne(String statementId,Object... objects) throws Exception;
}
