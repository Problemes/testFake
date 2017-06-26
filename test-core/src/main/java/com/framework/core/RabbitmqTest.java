package com.framework.core;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.junit.Test;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * Created by HR on 2017/6/20.
 */
public class RabbitmqTest {

    private final static String QUEUE_NAME = "hello";

    @Test
    public void testProducer() throws IOException, TimeoutException  {
        /** 创建连接工厂 */
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setHost("192.168.1.118");//27.115.63.102
        factory.setHost("192.168.1.118");
        factory.setUsername("hr");
        factory.setPassword("123456");
        factory.setPort(8080);

        /** 创建连接和通道 */
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        // 声明一个队列：名称、持久性的（重启仍存在此队列）、非私有的、非自动删除的
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 需发送的信息
        String message = "Hello YH!";
         /** 发送消息，使用默认的direct交换器 */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

        System.out.println(" [x] Sent '" + message + "'");

        if (channel != null){
            channel.close();
        }
        if (conn != null){
            conn.close();
        }
    }


    @Test
    public void testConsumer() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.118");
        factory.setUsername("hr");
        factory.setPassword("123456");
        factory.setPort(8080);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
             QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());System.out.println(" [x] Received '" + message + "'");
            }
    }

    //main驱动
    public RabbitmqTest() throws Exception{

        QueueConsumer consumer = new QueueConsumer("queue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        Producer producer = new Producer("queue");

        for (int i = 0; i < 100000; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number "+ i +" sent.");
        }
    }

    /**
     * @param args
     * @throws SQLException
     * @throws IOException
     */
    public static void main(String[] args) throws Exception{
        new RabbitmqTest();
    }

}
