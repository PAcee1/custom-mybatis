package com.enbuys.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.enbuys.io.Resources;
import com.enbuys.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author pace
 * @version v1.0
 * @Type XMLConfigBuilder.java
 * @Desc
 * @date 2020/4/11 16:41
 */
public class XMLConfigBuilder {

    // 因为要在多处使用Configuration，所以配置到成员变量上
    private Configuration configuration;

    public XMLConfigBuilder(){
        configuration = new Configuration();
    }

    /**
     * 解析输入流中的xml配置，封装到Configuration中
     * @param is
     * @return
     */
    public Configuration parseConfig(InputStream is) throws DocumentException, PropertyVetoException {
        /* 一、解析核心配置类 */
        // 使用dom4j解析配置
        Document document = new SAXReader().read(is);
        // 获取根节点，即<configuration>
        Element rootElement = document.getRootElement();
        // 获取property节点，存放数据库配置信息
        List<Element> nodes = rootElement.selectNodes("//property");// 使用xpath表达式
        // 使用Properties保存数据库配置
        Properties properties = new Properties();
        for (Element element : nodes) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        // 使用配置生成Druid连接池
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getProperty("driver"));
        dataSource.setUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUsername(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        // 将连接池配置到Configuration中
        configuration.setDataSource(dataSource);

        /* 二、解析映射配置类 */
        // <mapper>标签
        List<Element> mapperNodes = document.selectNodes("//mapper");
        // 循环解析所有mapper.xml
        for (Element mapperNode : mapperNodes) {
            // 获取mapper.xml的路径，resource属性
            String resource = mapperNode.attributeValue("resource");

            // 再次加载mapper配置文件到输入流，进行解析
            InputStream inputStream = new Resources().getResourceAsStream(resource);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parseMapper(inputStream);
        }

        return configuration;
    }
}
