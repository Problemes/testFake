package com.framework.core;

import com.framework.Util.MD5Util;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by HR on 2017/6/29.
 */
public class MD5Test {

    private String str;

    @Before
    public void before(){
        str = "test_data";
    }

    @Test
    public void testMD5(){
        String result0 = MD5Util.MD532(MD5Util.MD5Origin(str));

        String result1 = MD5Util.MD5String(str);

        System.out.println(result0);

        System.out.println(result1);

        System.out.println(result0.equals(result1));
    }
}
