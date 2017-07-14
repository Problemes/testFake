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
}
