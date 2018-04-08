package com.framework.core.mulitThread;

/**
 * 传统线程同步
 * Created by HR on 2018/3/14.
 */
public class TraditionalThreadSynchronized {

    public static void main(String[] args) {
        new TraditionalThreadSynchronized().init();
    }

    private void init() {
        final Outputer outputer = new Outputer();
        final Outputer outputer2 = new Outputer();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output(new String("param1111"));
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output(new String("param2222"));
                }

            }
        }).start();

    }

    static class Outputer {

        public void output(String name) {
            int len = name.length();
            synchronized (Outputer.class) {
                System.out.println(Thread.currentThread().getName()+ "--->");
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println("-------------------------split line------------------------");
            }
        }

        public synchronized void output2(String name) {
            int len = name.length();
            System.out.println(Thread.currentThread().getName()+ "--->");
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }

        public static synchronized void output3(String name) {
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }
    }
}
