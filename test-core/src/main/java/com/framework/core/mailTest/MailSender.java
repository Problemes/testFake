package com.framework.core.mailTest;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送类，可 单发，群发
 * Created by HR on 2017/9/14.
 */
public class MailSender {

    private final transient Properties properties = System.getProperties();    //发送邮件的properties文件

    private transient MailAuthenticator mailAuthenticator;  //邮件服务器登录验证

    private transient Session session;    //发送邮件的session

    /**
     *  初始化邮件发送器
     * @param smtpHostName SMTP邮件服务器地址
     * @param username 发送邮件的用户名
     * @param password 发送邮件的密码
     */
    public MailSender(final String smtpHostName,final String username,final String password) throws GeneralSecurityException {
        init(smtpHostName,username,password);
    }

    /**
     * 初始化邮件发送器，通过username发送的地址来解析服务器地址
     * @param username 发送邮件的用户名(地址)，并以此解析SMTP服务器地址
     * @param password 发送邮件的密码
     */
    public MailSender(final String username,final String password) throws GeneralSecurityException {
        //
        final String smtpHostName = "smtp." + username.split("@")[1];
        init(smtpHostName,username,password);
    }

    /**
     *
     * @param smtpHostName SMTP主机地址
     * @param username 发送邮件的用户名(地址)
     * @param password 密码
     */
    private void init(String smtpHostName, String username, String password) throws GeneralSecurityException {
        //初始化properties
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.host",smtpHostName);

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.ssl.socketFactory",sf);

        /*final String smtpPort = "465";
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.socketFactory.port", smtpPort);*/

        //验证
        mailAuthenticator = new MailAuthenticator(username,password);

        //创建Session
        session = Session.getInstance(properties);
    }

    /**
     * 发送邮件
     * @param recipient 收件人邮箱地址
     * @param subject 收件人邮箱地址
     * @param content 邮件内容
     * @throws MessagingException
     */
    public void send(String recipient,String subject,Object content) throws MessagingException {

        //创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);

        //设置发信人
        message.setFrom(new InternetAddress(mailAuthenticator.getUsername()));

        //设置收件人
        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipient));

        //设置主题
        message.setSubject(subject);

        //设置邮件内容
        message.setContent(content.toString(),"text/html;charset=utf-8");

        /*Transport transport = session.getTransport();
        transport.connect(mailAuthenticator.getUsername(), mailAuthenticator.getPassword());*/

        /** 发送邮件 */
        Transport.send(message);

        //transport.close();

    }


    /**
     * 群发邮件
     * @param recipients 收件人们
     * @param subject 主题
     * @param content 内容
     * @throws MessagingException
     */
    public void send(List<String> recipients, String subject, Object content) throws MessagingException {

        // 创建mime类型邮件
        final MimeMessage message = new MimeMessage(session);
        // 设置发信人
        message.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
        // 设置收件人们
        final int num = recipients.size();
        InternetAddress[] addresses = new InternetAddress[num];
        for (int i = 0; i < num; i++) {
            addresses[i] = new InternetAddress(recipients.get(i));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);
        // 设置主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setContent(content.toString(), "text/html;charset=utf-8");
        // 发送
        Transport.send(message);
    }

    /**
     * 发送邮件
     * @param recipient 收件人邮箱地址
     * @param mail 邮件对象
     * @throws MessagingException
     */
    public void send(String recipient, Mail mail) throws MessagingException {
        send(recipient,mail.getSubject(),mail.getContent());
    }

    /**
     * 发送邮件
     * @param recipients 收件人邮箱地址
     * @param mail 邮件对象
     * @throws MessagingException
     */
    public void send(List<String> recipients, Mail mail) throws MessagingException {
        send(recipients, mail.getSubject(), mail.getContent());
    }

}
