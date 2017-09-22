package com.framework.core;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程调度 ： http://www.cnblogs.com/lwbqqyumidi/p/3804883.html
 * Created by HR on 2017/6/28.
 */
public class ThreadTest {
    @Test
    public void testCacheThreadPool(){

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {

            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                }
            });
        }
    }

    class myRunnable implements Runnable{

        private boolean stop;

        @Override
        public void run() {
            if (!stop) {
                System.out.println("runnable once...");
            }
        }

        public void setStop(){
            this.stop = true;
        }
    }

    @Test
    public void testRunnable() throws InterruptedException {

        for (int i = 0; i < 10 ; i++){

            System.out.println(Thread.currentThread().getName() + "--" + i);

            if (i == 3){
                /** 调用线程对象引用的start()方法，使得该线程进入到就绪状态，此时此线程并不一定会马上得以执行，这取决于CPU调度时机 一个线程不能调用2次start方法*/
                Thread thread1 = new Thread(new myRunnable());
                thread1.start();
                /** 需等待thread1执行完了，在调回之前线程 */
                thread1.join();
                Thread.currentThread().getName();
            }
            if (i == 5){
                Thread.currentThread().getName();
                Thread.sleep(10);
                Thread thread2 = new Thread(new myRunnable());
                System.out.println("获取优先级：" + thread2.getPriority());
                thread2.setPriority(Thread.MAX_PRIORITY);
                System.out.println("获取优先级：" + thread2.getPriority());
                thread2.start();
                Thread.yield();
            }
        }
    }

    /**
     * FutureTask 实现又返回值的线程
     */
    class myCallable implements Callable<Integer>{
        private int i = 0;

        /** 带返回值的 */
        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (;i < 10 ; i++){
                System.out.println(Thread.currentThread().getName() + " --- " + i);
                sum += i;
            }
            return sum;
        }
    }

    @Test
    public void testFutureTask() throws InterruptedException {

        Callable<Integer> callable = new myCallable();
        FutureTask<Integer> ft = new FutureTask<Integer>(callable);

        for (int i = 0; i < 10 ; i++){
            System.out.println(Thread.currentThread().getName() + "--" + i);

            if (i == 3){
                /** 调用线程对象引用的start()方法，使得该线程进入到就绪状态，此时此线程并不一定会马上得以执行，这取决于CPU调度时机 */
                Thread thread1 = new Thread(ft);
                thread1.start();
            }
            if (i == 5){
                Thread.currentThread().getName();
                Thread thread2 = new Thread(ft);
                thread2.start();
            }
        }
        System.out.println("main执行完毕....");
        try {
            int result = ft.get();
            System.out.println("Result:" + result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /*====================线程安全=========================*/
    /**
     * 问题在于Java多线程环境下的执行的不确定性。CPU可能随机的在多个处于就绪状态中的线程中进行切换，
     * 因此，很有可能出现如下情况：
     * 当thread1执行到//1处代码时，判断条件为true，
     * 此时CPU切换到thread2，执行//1处代码，发现依然为真，然后执行完thread2，
     * 接着切换到thread1，接着执行完毕。此时，就会出现判断失效结果。
     *
     * 解决：
     *  1.public synchronized void run() {...};加同步锁，只有一个线程能拿到同步锁
     *  2.synchronized (obj) {...} :同步代码块；
     *  3.// 显示定义Lock同步锁对象，此对象与共享资源具有一对一关系
     *     private final Lock lock = new ReentrantLock();
     *
     *     public void m(){
     *         // 加锁
     *         lock.lock();
     *         //...  需要进行线程安全同步的代码
     *         // 释放Lock锁
     *         lock.unlock();
     *     }
     */

    /*====================线程通讯=========================*/

    /**
     * wait()：导致当前线程等待并使其进入到等待阻塞状态。直到其他线程调用该同步锁对象的notify()或notifyAll()方法来唤醒此线程。

     notify()：唤醒在此同步锁对象上等待的单个线程，如果有多个线程都在此同步锁对象上等待，则会任意选择其中某个线程进行唤醒操作，
     只有当前线程放弃对同步锁对象的锁定，才可能执行被唤醒的线程。

     notifyAll()：唤醒在此同步锁对象上等待的所有线程，只有当前线程放弃对同步锁对象的锁定，才可能执行被唤醒的线程。
     */


}
