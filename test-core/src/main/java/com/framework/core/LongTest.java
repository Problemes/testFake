package com.framework.core;

import org.junit.Test;

/**
 * Created by HR on 2017/7/25.
 */
public class LongTest {

    /**
     * Long类型的数据对比
     */
    @Test
    public void testLongEquals(){
        Long l1 = new Long(100);
        Long l2 = new Long(100);
        System.out.println(l1 == l2);
        System.out.println(l1.equals(l2));

        Long l3 = 0l;
        Long l4 = 0l;
        System.out.println(l3 == l4);
        System.out.println(l3.equals(l4));
    }
}
