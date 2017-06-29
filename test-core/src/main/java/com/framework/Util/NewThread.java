package com.framework.Util;


/**
 * Created by HR on 2017/6/28.
 */
public class NewThread extends Thread {

    @Override
    public void run() {

        while (!Thread.interrupted()){
            System.out.println("----------subscribe operation start-------");
            System.out.println(System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
