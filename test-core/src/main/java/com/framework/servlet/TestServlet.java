package com.framework.servlet;

import com.framework.Util.NewThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by HR on 2017/6/28.
 */
public class TestServlet extends HttpServlet {

    private NewThread testThread;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (testThread != null && testThread.isInterrupted()) {
            testThread.interrupt();
        }
        System.out.println("----------subscribe operation end-------");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        if (testThread == null) {
            testThread = new NewThread();
            testThread.start(); // servlet 上下文初始化时启动 socket
        }
    }
}
