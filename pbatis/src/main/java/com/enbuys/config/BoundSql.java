package com.enbuys.config;

import com.enbuys.utils.ParameterMapping;

import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type BoundSql.java
 * @Desc
 * @date 2020/4/11 22:02
 */
public class BoundSql {

    // 解析后的sql，占位符为？
    private String sql;
    // 解析出来原本占位符位置的字段集合，比如#{id}，存的就是id
    private List<ParameterMapping> parameterMappingList;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
