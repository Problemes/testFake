package com.framework.core;

import org.junit.Test;

/**
 * 数学运算
 * Created by HR on 2018/9/13.
 */
public class MathTest {

    /**
     * 计算距离最近的2的指数
     */
    @Test
    public void test(){
        int a = 3;
        int b = 9;
        int c = 39;
        System.out.println(2<<(Integer.toBinaryString(a-1).length()-1));
        System.out.println(2<<(Integer.toBinaryString(b-1).length()-1));
        System.out.println(2<<(Integer.toBinaryString(c-1).length()-1));
    }

}
