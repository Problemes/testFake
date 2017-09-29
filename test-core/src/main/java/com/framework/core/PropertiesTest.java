package com.framework.core;

import net.sf.json.JSONArray;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Java Properties 类主要用于读取java的配置文件
 * http://www.cnblogs.com/bakari/p/3562244.html
 * Created by HR on 2017/9/22.
 */
public class PropertiesTest {

    public static Properties getProperties(String path){

        Properties properties = new Properties();
        try {
            //path必须是系统的路径，不可行。
            properties.load(new FileInputStream(path));

            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()){
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                System.out.println(key + ":" + value);
            }

            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 测试获取配置文件
     */
    @Test
    public void testProperties() throws IOException {
        Properties p = new Properties();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        p.load(is);

        System.out.println(p.getProperty("java"));

        Properties thisPp = this.getProperties("D:\\github\\testFake\\test-core\\src\\main\\resources\\application.properties");
        System.out.println("this.Properties : " + thisPp.getProperty("java"));

        /** 读取系统的properties文件 */
        Properties sp = System.getProperties();
        sp.list(System.out);
        System.out.println(JSONArray.fromObject(sp));
    }
}
