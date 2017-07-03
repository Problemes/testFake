package com.framework.core;

import org.junit.Before;
import org.junit.Test;

import java.util.StringTokenizer;

/**
 * Created by HR on 2017/6/29.
 */
public class TestString {

    String str = null;

    @Before
    public void before(){
        str = "test_data_data1_data2";
    }

    /**
     * 测试以某符号分割字符串
     */
    @Test
    public void testStringTokenizer(){

        StringTokenizer st = new StringTokenizer(str,"_",false);
        while (st.hasMoreElements()){
            //System.out.println("ST:" + st.nextToken());
            System.out.println("ST_:" + st.nextToken("_"));
        }

        StringTokenizer st2 = new StringTokenizer(str,"_",true);
        while (st2.hasMoreElements()){
            //System.out.println("ST:" + st.nextToken());
            System.out.println("ST_:" + st2.nextToken("_"));
        }

        String[] sp =  str.split("_");
        for (String spson:sp){
            System.out.println("神威分割：" + spson);
        }
    }

    @Test
    public void testStringEquals(){
        //直接赋值是从常量池中拿，所以相等
        String a = "abc";
        String b = "abc";
        System.out.println( "a == b : " + (a == b));
        System.out.println( "a equals b : " + (a.equals(b)));

        //new 是直接放入堆内存中。所以不相等
        String aa = new String("abc");
        System.out.println("a == aa: " + (a == aa));
        System.out.println( "a equals aa : " + (a.equals(aa)));
    }

    @Test
    public void testStringSB(){
        //StringBuffer && StringBuilder 可变数组，方法一样，前者线程安全(每个方法加了同步)
        //处理速度： StringBuilder > StringBuffer > String

        StringBuffer sbuffer = new StringBuffer("test_data");
        sbuffer.append("_data1");
        System.out.println(sbuffer);
        sbuffer.insert(9,"_insert");
        System.out.println(sbuffer);
    }

}
