package com.enbuys.pojo;

/**
 * @author pace
 * @version v1.0
 * @Type MappedStatement.java
 * @Desc 映射配置实体
 * @date 2020/4/11 16:15
 */
public class MappedStatement {

    // id
    private String id;
    // 入参
    private String paramterType;
    // 返回值
    private String resultType;
    // sql
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamterType() {
        return paramterType;
    }

    public void setParamterType(String paramterType) {
        this.paramterType = paramterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
