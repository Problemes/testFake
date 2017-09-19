package com.framework.core.mailTest;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 服务器邮箱登录验证(用于发送邮件的邮件)
 * Created by HR on 2017/9/14.
 */
public class MailAuthenticator extends Authenticator {

    private String username;    //登录邮箱的用户名
    private String password;    //登录邮箱的密码

    //构造方法初始化用户名和密码
    public MailAuthenticator(String username,String password){
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username,password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
