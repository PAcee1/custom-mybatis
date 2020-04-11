package com.enbuys.executor;

import com.enbuys.pojo.Configuration;
import com.enbuys.pojo.MappedStatement;

import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type SimpleExecutory.java
 * @Desc
 * @date 2020/4/11 18:15
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... objects) {
        return null;
    }
}
