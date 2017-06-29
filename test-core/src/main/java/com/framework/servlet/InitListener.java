package com.framework.servlet;

import com.framework.Util.NewThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by HR on 2017/6/28.
 */
public class InitListener implements ServletContextListener {



    private NewThread testThread;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        testThread = new NewThread();
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
