package com.enbuys.sqlSession;

import com.enbuys.executor.Executor;
import com.enbuys.executor.SimpleExecutor;
import com.enbuys.pojo.Configuration;
import com.enbuys.pojo.MappedStatement;

import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type DefaultSqlSession.java
 * @Desc
 * @date 2020/4/11 17:52
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public <T> List<T> selectList(String statementId,Object... objects) {
        // 首先拿到具体的映射对象
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

        // 这里我们再次进行解耦，具体的JDBC代码封装到Executor类中
        Executor executor = new SimpleExecutor();
        List<Object> list = executor.query(configuration, mappedStatement, objects);
        return (List<T>) list;
    }

    @Override
    public <E> E selectOne(String statementId, Object... objects) {
        // 直接使用selectList方法即可
        List<Object> list = selectList(statementId, objects);
        // 判断list数量是否等于1
        if(list.size() == 1){
            return (E) list.get(0);
        }else {
            throw new RuntimeException("查询结果不存在或数量大于1");
        }
    }
}
