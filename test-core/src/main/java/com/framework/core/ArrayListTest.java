package com.framework.core;

import com.framework.entity.Father;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * 分解字符串成数组
     */
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

    /**
     * 求两个list或数组的并集差集
     */
    @Test
    public void teatListDiff(){

        List<Long> list1 =new ArrayList();
        list1.add(1111l);
        list1.add(2222l);
        list1.add(3333l);
//        list1.add(4444l);
//        list1.add(5555l);

        List<Long> list2 =new ArrayList();
        list2.add(3333l);
        list2.add(4444l);
        list2.add(5555l);

        /** 并集 */
//        list1.addAll(list2);
        System.out.println(list1.toString());

        /** 交集 */
//        System.out.println(list1.retainAll(list2));
        System.out.println("交集1：" + list1);
        System.out.println("交集2：" + list2);

        /** 差集 */
        System.out.println(list1.removeAll(list2));
        System.out.println("差集1：" + list1);
        System.out.println("差集2：" + list2);

        /** 判断两个集合是否相等:错的，，待定 */
        System.out.println(list1.contains(list2));
    }

    /**
     * 数组转List时候，Arrays.asList(...);这种转化的list是不能操作内容的
     * 由于数组是固定长度的，list转数组的时候最好List.toArray(new String[List.size]);
     */
    @Test
    public void teatVariableArrayToList(){

        String[] arg0 = new String[2];
        String[] arg1 = new String[3];

        arg0[0] = "";
        arg0[1] = "";
        arg1[0] = "";
        arg1[1] = "";
        arg1[0] = "";

        Arrays.asList(arg0);
        new ArrayList<String>(Arrays.asList(arg1));//可操作

    }

}
