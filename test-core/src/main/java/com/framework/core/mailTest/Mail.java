package com.framework.core.mailTest;

import org.springframework.stereotype.Controller;

import java.io.Serializable;

/**
 * 封装了mail的主题和内容的pojo
 * Created by HR on 2017/9/14.
 */

@Controller
public class Mail implements Serializable {

    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
