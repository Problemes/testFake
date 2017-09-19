package com.framework.core.mailTest;

import org.junit.Test;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

/**
 * java实现发邮件
 * Created by HR on 2017/9/14.
 */
public class EmailTest {

    /**
     * javaMail发邮件过程：
     * 1.构建一个继承自java.mail.Authenticator的类，并重写getPasswordAuthentication()方法。此类是用于登录校验的，确保有对该邮箱的发送邮件权利
     * 2.构建一个properties文件，存放SMTP服务器的参数
     * 3.通过1,2构建的文件和类，构建一个java.mail.Session,Session的创建相当于登录邮箱一样，新建后就可以新建邮件了
     * 4.用java.mail.internet.MimeMessage构建内容，指定发送人，收件人，主题，内容。
     * 5.使用java.mail.Transport工具类发送邮件
     */



    @Test
    public void sendEmailTest() throws MessagingException, GeneralSecurityException {

        MailSender mailSender = new MailSender("smtp.qq.com","1191624317@qq.com","hnhincluivgnijfi");
        String recipient = "haoran.liang@yinghe-edu.com";
        mailSender.send(recipient,"mail test subject","mail test content");
    }
}

