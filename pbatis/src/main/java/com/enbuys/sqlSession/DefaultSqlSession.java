package com.enbuys.sqlSession;

import com.enbuys.executor.Executor;
import com.enbuys.executor.SimpleExecutor;
import com.enbuys.pojo.Configuration;
import com.enbuys.pojo.MappedStatement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
    public <T> List<T> selectList(String statementId,Object... objects) throws Exception {
        // 首先拿到具体的映射对象
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);

        // 这里我们再次进行解耦，具体的JDBC代码封装到Executor类中
        Executor executor = new SimpleExecutor();
        List<Object> list = executor.query(configuration, mappedStatement, objects);
        return (List<T>) list;
    }

    @Override
    public <E> E selectOne(String statementId, Object... objects) throws Exception {
        // 直接使用selectList方法即可
        List<Object> list = selectList(statementId, objects);
        // 判断list数量是否等于1
        if(list.size() == 1){
            return (E) list.get(0);
        }else {
            throw new RuntimeException("查询结果不存在或数量大于1");
        }
    }

    @Override
    public <T> T getMapper(Class<?> clazz) {
        // 使用JDK动态代理
        Object proxyInstance = Proxy.newProxyInstance(this.getClass().getClassLoader(),
            new Class[]{clazz}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // 实际调用的还是SqlSession中的select方法，所以需要两个参数
                    // 一、首先需要获取statementId = 全限定类名.方法名
                    // 全限定类名
                    String className = method.getDeclaringClass().getName();
                    String methodName = method.getName();
                    String statementId = className + "." + methodName;

                    // 二、需要入参objects，就是args

                    // 三、调用Select方法，这里我们需要判断使用哪个方法
                    // 因为我们只是简单实现，所以这里我使用如果有参数执行selectOne，如果没有参数执行selectList
                    // 在Mybatis中，对于这里的判断肯定更加严谨完善
                    // 我们只是需要掌握它的思路，具体代码实现有兴趣可以进一步研究
                    if(args != null){
                        Object o = selectOne(statementId, args);
                        return o;
                    }else {
                        List<Object> objects = selectList(statementId, args);
                        return objects;
                    }
                }
            });
        return (T) proxyInstance;
    }
}
