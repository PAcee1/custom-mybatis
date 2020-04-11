package com.enbuys.config;

import com.enbuys.pojo.Configuration;
import com.enbuys.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author pace
 * @version v1.0
 * @Type XMLMapperBuilder.java
 * @Desc
 * @date 2020/4/11 17:18
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * 解析mapper.xml文件
     * @param is
     * @return
     * @throws DocumentException
     */
    public Configuration parseMapper(InputStream is) throws DocumentException {
        Document document = new SAXReader().read(is);
        // 获得<mapper>标签
        Element rootElement = document.getRootElement();
        // 获取namespace属性
        String namespace = rootElement.attributeValue("namespace");

        // 获取select标签们
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            // 获取sql，id，入参，返回值
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            String sqlText = element.getTextTrim();
            // 封装到MappedStatement对象
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sqlText);

            // 保存到Configuration的容器中
            // key为namespace.id
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }
        return configuration;
    }
}
