package com.framework.servlet;

import com.framework.Util.TestThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by HR on 2017/6/28.
 */
public class InitListener implements ServletContextListener {



    private TestThread testThread;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        testThread = new TestThread();
        testThread.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        if (testThread != null && testThread.isInterrupted()){
            testThread.interrupt();
        }
        System.out.println("----------subscribe operation end-------");
    }
}
