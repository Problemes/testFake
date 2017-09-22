package com.framework.core;

/**
 * GC垃圾回收时的finalize方法执行顺序
 * 垃圾回收机制：第一次认定为不可到达的对象时会调用一次finalize方法，然后第二次会真正的回收，
 * 第二次回收时候，不会再调用finalize方法了。
 * 一个对象的finalize只可能运行一次，因为调用一次后，后面就会被收回内存了。
 * Created by HR on 2017/9/20.
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public String name;

    public FinalizeEscapeGC(String name) {
        this.name = name;
    }

    public void isAlive() {
        System.out.println("yes, i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        System.out.println(this.name);
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGC("abc");
        //第一次释放
        SAVE_HOOK = null;
        //第一次垃圾回收，名为abc的FinalizeEscapeGC实例对象的finalize()方法执行，此时全局静态变量 SAVE_HOOK又重新指向了改对象，使得该对象“复活”
        System.gc();
        Thread.sleep(2000);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead");
        }

        //再次切断引用链，43行，第二次垃圾回收，该对象的finalize()方法不会再执行了。该对象在堆中的空间被释放。
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(2000);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead");
        }
    }
}
