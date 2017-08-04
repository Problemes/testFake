package com.framework.core;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import java.util.Map;

/**
 * Created by HR on 2017/8/4.
 */
public class MapTest {

    /***
     * 测试Map的key类型
     */
    @Test
    public void testMapKey(){

        Map<Integer ,String> intMap = new HashedMap();
        intMap.put(1,"yi");
        intMap.put(2,"er");
        intMap.put(3,"san");

        Map<Long ,String> longMap = new HashedMap();
        longMap.put(1l,"long1");
        longMap.put(2l,"long2");
        longMap.put(3l,"long3");

        for (Integer key : intMap.keySet()){
            System.out.println(intMap.get(key));
        }

        for (Long key : longMap.keySet()){
            System.out.println(longMap.get(key));
        }

        System.out.println(intMap.size());
    }
}
