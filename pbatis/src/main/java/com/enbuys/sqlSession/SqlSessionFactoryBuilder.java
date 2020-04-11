package com.enbuys.sqlSession;

import com.enbuys.config.XMLConfigBuilder;
import com.enbuys.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author pace
 * @version v1.0
 * @Type SqlSessionFactoryBuilder.java
 * @Desc
 * @date 2020/4/11 16:40
 */
public class SqlSessionFactoryBuilder {

    /**
     * 解析配置，创建SqlSessionFactory
     * @param is
     * @return
     */
    public SqlSessionFactory build(InputStream is) throws DocumentException, PropertyVetoException {
        // 一、使用dom4j解析配置文件
        // 将解析方法再次封装，封装到具体解析类中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(is);

        // 二、创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
