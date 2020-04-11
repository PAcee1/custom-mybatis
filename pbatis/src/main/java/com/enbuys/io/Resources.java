package com.enbuys.io;

import java.io.InputStream;

/**
 * @author pace
 * @version v1.0
 * @Type Resource.java
 * @Desc 加载配置文件类
 * @date 2020/4/11 15:56
 */
public class Resources {

    // 根据配置文件路径，获取字节输入流，存储到内存
    public InputStream getResourceAsStream(String path){
        // 直接调用ClassLoader的方法
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
