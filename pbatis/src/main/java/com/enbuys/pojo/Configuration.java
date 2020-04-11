package com.enbuys.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pace
 * @version v1.0
 * @Type Configuration.java
 * @Desc 核心配置实体
 * @date 2020/4/11 16:15
 */
public class Configuration {

    // 数据库信息
    private DataSource dataSource;

    // Mapper信息封装到Configuration中 String = statementId = namespace.id
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
