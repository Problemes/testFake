package com.framework.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.GeneralSecurityException;

/**
 * spring 加载类测试
 * Created by HR on 2017/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringTest {

    @Autowired
    private org.springframework.mail.MailSender mailSender;

    @Autowired
    private SimpleMailMessage mailMessage;

    @Test
    public void testSpring() throws GeneralSecurityException {
        System.out.println(mailMessage.toString());

        mailMessage.setSubject("new Theme");
        mailMessage.setText("new Text" + "\r\n" + "啥啥啥？");
        mailMessage.setCc("1240065597@qq.com");
        mailSender.send(mailMessage);

    }

}
