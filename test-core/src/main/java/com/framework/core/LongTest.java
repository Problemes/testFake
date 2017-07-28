package com.framework.core;

import org.junit.Test;

/**
 * Created by HR on 2017/7/25.
 */
public class LongTest {

    @Test
    public void testLongEquals(){
        Long l1 = new Long(100);
        Long l2 = new Long(100);
        System.out.println(l1 == l2);
        System.out.println(l1.equals(l2));
    }
}
