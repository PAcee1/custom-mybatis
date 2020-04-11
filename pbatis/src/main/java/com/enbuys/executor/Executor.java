package com.enbuys.executor;

import com.enbuys.pojo.Configuration;
import com.enbuys.pojo.MappedStatement;

import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type Executor.java
 * @Desc
 * @date 2020/4/11 18:14
 */
public interface Executor {
    <T> List<T> query(Configuration configuration, MappedStatement mappedStatement,Object... objects);
}
