package com.framework.core;

import com.framework.entity.Father;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by HR on 2017/7/11.
 */
public class ArrayListTest {

    @Before
    public void before(){
        Father father = new Father();
    }

    @Test
    public void testArrayWithObject(){
        Father[] fas = new Father[5];
        for (Father father : fas){
            System.out.println(father);
        }
    }

    /**
     * 当数组参数为空时，初始化为其给各个数据类型的默认值
     */
    @Test
    public void testArrayParamNull(){
        int[] array = new int[3];
        for (int i : array){
            System.out.println(i);
        }
        Long[] arrayLong = new Long[3];
        for (Long i : arrayLong){
            System.out.println(i);
        }
        String[] arrayString = new String[3];
        for (String i : arrayString){
            System.out.println(i);
        }
    }

    @Test
    public void testArrayWithSpit(){
        String var = "100:200:300:400:500";
        String[] vars = var.split(":");
        StringBuffer sb = new StringBuffer(vars[0]);
        for (int i = 0; i < vars.length; i++){
            System.out.println(vars[i]);
            sb.append(vars[i]);
        }
        System.out.println(sb.toString());
    }
}
