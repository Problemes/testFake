package com.framework.core;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式示例
 * https://www.cnblogs.com/lzq198754/p/5780340.html
 * 理论：https://www.cnblogs.com/zery/p/3438845.html
 * 常用的20个正则表达式示例：https://www.jianshu.com/p/e7bb97218946
 * Created by HR on 2017/12/7.
 */
public class regexTest {

    @Test
    public void testValidateEmail(){

        // 要验证的字符串
        String str = "service@xsoftlab.net";
        // 邮箱验证规则
        String regEx = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();

        System.out.println("验证结果：--->>" + rs);

    }

    @Test
    public void testValidateString(){

        // 要验证的字符串
//        String str = "baike.xsoftlab.net";
        String str = "/v1/student/classId/1000001/studentInfo";
        // 正则表达式规则
//        String regEx = "baike.*";
        String regEx = "/v1/student/classId/{1,}/studentInfo";

                // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        boolean rs = matcher.find();

        System.out.println("验证结果：--->>" + rs);

    }

    @Test
    public void testValidateDate(){
        String regEx = "^(\\d{1,4})(-|\\/)(\\d{1,2})\\2(\\d{1,2})$";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("1992-02-10");

        boolean result = matcher.matches();

        System.out.println(result);
    }

}
