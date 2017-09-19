package com.framework.core;


/**
 * http://www.blogjava.net/kenzhh/archive/2013/03/15/357824.html
 * Created by HR on 2017/9/8.
 */
public class SingletonTest {

    private SingletonTest(){}

    /**
     * 懒汉模式
     */
    /*private static SingletonTest instance;*/

    /**
     * 懒汉模式 线程不安全 不适合多线程
     * @return
     */
    /*public static SingletonTest getInstance(){
        if (instance == null){
            instance = new SingletonTest();
        }
        return instance;
    }*/

    /**
     * 懒汉模式 单机部署线程安全 效率低，用的少
     * @return
     */
    /*public static synchronized SingletonTest getInstance(){
        if (instance == null){
            instance = new SingletonTest();
        }
        return instance;
    }*/


    /*==========================================================*/

    /**
     * 饿汉模式
     */
    /*private static SingletonTest instance = new SingletonTest();*/

    /**
     * 利用classLoader类加载机制避免了单机多线程问题，在类加载时就实例化，这样没有实现lazy loading的理念
     * @return
     */
    /*public static SingletonTest getInstance(){
        return instance;
    }*/

    /*============================================================*/

    /**
     * 静态内部类与饿汉模式类似，但是实现了lazy loading
     */
    public static class SingletonHolder{
        private static final SingletonTest instance = new SingletonTest();
    }
    public static SingletonTest getInstance(){
        return SingletonHolder.instance;
    }

    /*============================================================*/


    //特殊的枚举实现： 能避免多线程同步问题，能防止反序列化创建新对象，很好用 jdk1.5以上版本
    /*public enum Singleton{
        INSTANCE;
        public void whateverMethon(){

        }
    }*/




}
