package com.framework.core;

import com.framework.ReflectUtil.TestInvocationHandler;
import com.framework.entity.Father;
import com.framework.entity.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;

/**
 * Created by HR on 2017/6/7.
 */
public class BaseTest {

    @Before
    public void testBefore(){
        System.out.println("Testing begin...");
    }

    public class T implements Serializable{
        public String getPria() {
            return pria;
        }
        public void setPria(String pria) {
            this.pria = pria;
        }
        public String getPubb() {
            return pubb;
        }
        public void setPubb(String pubb) {
            this.pubb = pubb;
        }

        private String pria = "a";
        public  String pubb = "b";

        public void run(){

        }

        T(){}

        T(String a,String b){
            this.pria = a;
            this.pubb = b;
        }
    }

    //测试反射api
    @Test
    public void testClassApi() throws ClassNotFoundException {

        T t = new T();
        System.out.println("class Name:" + t.getClass().getName());
        System.out.println("classLoader : " + t.getClass().getClassLoader());

        System.out.println("classForClass : " + Class.forName("com.framework.core.BaseTest$T").getName());
        System.out.println("classNewClass : " + new T().getClass().getName());
        System.out.println("classNewClass : " + T.class);

        System.out.println("superClass : " + T.class.getSuperclass());
        System.out.println("superInterface : " + T.class.getInterfaces()[0].getName());

        System.out.println(t.pria);
        System.out.println(new T("aaa","bbb").pria);

        Assert.assertEquals( T.class.getMethods() + "有问题", 5, 5); //预期值与程序输出不一样
    }

    //反射实例化一个对象
    @Test
    public void testReflectApi() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.framework.entity.Model");

        Model model = (Model)clazz.newInstance();
        model.setVar1("InstanceVar1");
        model.setVar2("InstanceVar2");
        System.out.println("ClassToObjectByInstance : " + model.getVar1() + "---" + model.getVar2());

        Constructor<?> constructor[] = clazz.getConstructors();

        Model modelCons = (Model) constructor[0].newInstance();
        modelCons.setVar1("var1");
        System.out.println("----->>>" + modelCons.getVar1());

        System.out.println("===============本类属性===============");
        Field field[] = clazz.getDeclaredFields();
        for (int i=0;i<field.length;i++){
            System.out.println("Vars : " + field[i].getName());
            System.out.println("Modify : " + Modifier.toString(field[i].getModifiers()));
            System.out.println("Name : " + field[i].getType().getName());
        }

        System.out.println("==========实现的接口或者父类的属性==========");
        Field fieldFI[] = clazz.getFields();
        for (int i=0;i<fieldFI.length;i++){
            System.out.println("Vars : " + fieldFI[i].getName());
            System.out.println("Modify : " + Modifier.toString(fieldFI[i].getModifiers()));
            System.out.println("Name : " + fieldFI[i].getType().getName());
        }
        System.out.println("===============获取所有方法===============");
        Method method[] = clazz.getMethods();
        for (Method m : method){
            System.out.println("" + m.getName());
            System.out.println("returnType : " + m.getReturnType());
            Class cs[] =  m.getParameterTypes();
            for (Class c : cs){
                System.out.println("Params : " + c.getName());
                System.out.println("Modify : " + Modifier.toString(c.getModifiers()));
            }
            System.out.println("" + m.getExceptionTypes());

        }
        System.out.println("===============执行某个方法===============");
        Method exeMth = clazz.getMethod("run");
        Object object = exeMth.invoke(clazz.newInstance());
        System.out.println("ExecMethodReturn : " + object);

        Method exeMthWithParam = clazz.getMethod("run",int.class);
        Object object1 = exeMthWithParam.invoke(clazz.newInstance(),123);
        System.out.println("ExecMethodWithParamReturn : " + object1);

        System.out.println("===============操作某个类属性===============");
        Model obj = (Model) clazz.newInstance();
        // 可以直接对 private 的属性赋值
        Field fieldVar = clazz.getDeclaredField("var1");
        fieldVar.setAccessible(true);
        fieldVar.set(obj, "Java反射机制");
        System.out.println("var1 : " + fieldVar.get(obj));


    }



    /**
     * 在java中有三种类类加载器。
     *
     * 1）Bootstrap ClassLoader 此加载器采用c++编写，一般开发中很少见。
     *
     * 2）Extension ClassLoader 用来进行扩展类的加载，一般对应的是jrelibext目录中的类
     *
     * 3）AppClassLoader 加载classpath指定的类，是最常用的加载器。同时也是java中默认的加载器。
     *
     * 如果想要完成动态代理，首先需要定义一个InvocationHandler接口的子类，已完成代理的具体操作。
     *
     */
    @Test
    public void testReflectProxy() throws IllegalAccessException, InstantiationException {
        //类加载器
        ClassLoader classLoader = Model.class.getClassLoader();

        System.out.println(classLoader.getClass().getName());

        TestInvocationHandler testInvocationHandler = new TestInvocationHandler();
        Serializable father = (Serializable) testInvocationHandler.bind(new Model());

        //father.run();


    }

}
