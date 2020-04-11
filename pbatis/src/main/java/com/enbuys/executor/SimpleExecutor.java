package com.enbuys.executor;

import com.enbuys.config.BoundSql;
import com.enbuys.pojo.Configuration;
import com.enbuys.pojo.MappedStatement;
import com.enbuys.utils.GenericTokenParser;
import com.enbuys.utils.ParameterMapping;
import com.enbuys.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type SimpleExecutor.java
 * @Desc 实际进行JDBC操作的执行器
 * @date 2020/4/11 18:15
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... objects) throws Exception {
        /* 1.获取连接 */
        Connection connection = configuration.getDataSource().getConnection();

        /* 2.准备sql */
        // select * from user where id = #{id} and username = #{username}
        // 需要将sql转换成 select * from user where id = ? and username = ?
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql); // 解析后的对象

        /* 3.获取预处理对象 */
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

        /* 4.添加参数   需要使用反射 */
        // 获取参数类型
        String paramterType = mappedStatement.getParamterType(); // 参数对象全路径
        // 通过路径，获取到这个对象
        Class<?> clazz = Class.forName(paramterType);
        // 获取参数名称集合
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        // 循环，通过反射获取该名称的值，并设置到预处理对象中
        for (int i = 0; i < parameterMappingList.size() ; i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            // 反射
            Field declaredField = clazz.getDeclaredField(content);
            // 暴力破解
            declaredField.setAccessible(true);
            // 获取值
            Object o = declaredField.get(objects[0]);
            // 设置到预处理对象中
            preparedStatement.setObject(i+1,o);
        }

        /* 5.执行 */
        ResultSet resultSet = preparedStatement.executeQuery();

        /* 6.封装结果   需要使用反射 */
        // 获取结果类型
        String resultType = mappedStatement.getResultType(); // 结果对象的全路径
        // 通过路径获取结果对象Class
        Class<?> resultClazz = Class.forName(resultType);

        // 保存结果
        List<Object> resultList = new ArrayList<>();

        // 循环封装结果
        while (resultSet.next()){
            // 获取结果类对象
            Object o = resultClazz.newInstance();

            // 获取元数据，主要目的是获取字段名
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i = 1 ; i < metaData.getColumnCount() ; i++){ // 元数据下标从1开始
                // 获取字段
                String columnName = metaData.getColumnName(i);
                // 获取值
                Object value = resultSet.getObject(columnName);

                // 通过反射内省，设置对象属性与值
                // 通过字段名和类Class，获取属性描述器，用来写入属性的值
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultClazz);
                // 设置写入属性值的方法，类似于set方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 设置对象，以及对应的值，进行属性值写入
                writeMethod.invoke(o,value);
            }

            // 保存对象到集合
            resultList.add(o);
        }

        return (List<T>) resultList;
    }

    /**
     * 转换sql，将#{}占位符替换成？，并取出占位符中的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        // 这里直接拿Mybatis的工具类进行转换
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{","}",handler);

        // 解析后的sql
        String parseSql = parser.parse(sql);
        // 解析后sql中的站位字段集合
        List<ParameterMapping> parameterMappings = handler.getParameterMappings();

        // 封装到BoudSql中
        BoundSql boundSql = new BoundSql();
        boundSql.setSql(parseSql);
        boundSql.setParameterMappingList(parameterMappings);
        return boundSql;
    }
}
