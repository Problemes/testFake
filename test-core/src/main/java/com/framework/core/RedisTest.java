package com.framework.core;

import com.framework.cache.provider.redis.RedisCacheProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;
import java.util.Set;

/**
 * Created by HR on 2017/6/20.
 */
public class RedisTest {

    RedisCacheProvider redisCacheProvider = RedisCacheProvider.getInstance();

    Jedis jedisCache = redisCacheProvider.getJedis();

    public RedisTest() throws Exception {
    }


    @Test
    public void testRedisCommand(){
        jedisCache.hset("ss","s1","k1");
        System.out.println(jedisCache.hget("ss","s1"));
        if(jedisCache != null){
            jedisCache.close();
        }
    }

    /** 非持久化订阅 */
    //订阅者消息处理器
    class PrintListener extends JedisPubSub{

        @Override
        public void onMessage(String channel, String message) {
            String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            System.out.println("message receive:" + message + ",channel:" + channel + "..." + time);
            //此处我们可以取消订阅
            if(message.equalsIgnoreCase("quit")){
                this.unsubscribe(channel);
            }
        }
    }

    //消息发布端
    class PubClient {

        private Jedis jedis;//
        public PubClient(String host,int port){
            jedis = new Jedis(host,port);
        }

        public void pub(String channel,String message){
            jedis.publish(channel, message);
        }

        public void close(String channel){
            jedis.publish(channel, "quit");
            jedis.del(channel);//
        }
    }

    //取消订阅端
    class SubClient {

        private Jedis jedis;//

        public SubClient(String host,int port){
            jedis = new Jedis(host,port);
        }

        public void sub(JedisPubSub listener, String channel){
            jedis.subscribe(listener, channel);
            //此处将会阻塞，在client代码级别为JedisPubSub在处理消息时，将会“独占”链接
            //并且采取了while循环的方式，侦听订阅的消息
            //
        }
    }

    @Test
    public void testNotPersistent() throws InterruptedException {

        PubClient pubClient = new PubClient("192.168.1.118",6379);
        final String channel = "pubsub-channel";
        pubClient.pub(channel, "before1");
        pubClient.pub(channel, "before2");
        Thread.sleep(2000);
        //消息订阅着非常特殊，需要独占链接，因此我们需要为它创建新的链接；
        //此外，jedis客户端的实现也保证了“链接独占”的特性，sub方法将一直阻塞，
        //直到调用listener.unsubscribe方法
        Thread subThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    SubClient subClient = new SubClient("192.168.1.118",6379);
                    System.out.println("----------subscribe operation begin-------");
                    JedisPubSub listener = new PrintListener();
                    //在API级别，此处为轮询操作，直到unsubscribe调用，才会返回
                    subClient.sub(listener, channel);
                    System.out.println("----------subscribe operation end-------");
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        subThread.start();
        int i=0;
        while(i < 10){
            String message = RandomStringUtils.random(6, true, true);//apache-commons
            pubClient.pub(channel, message);
            i++;
            Thread.sleep(1000);
        }
        //被动关闭指示，如果通道中，消息发布者确定通道需要关闭，那么就发送一个“quit”
        //那么在listener.onMessage()中接收到“quit”时，其他订阅client将执行“unsubscribe”操作。
        pubClient.close(channel);
        //此外，你还可以这样取消订阅  listener.unsubscribe(channel);
    }



    /** 可持久化的订阅 */
    //消息处理类

    private static String SUBSCRIBE_CENTER = "pp";

    class PPrintListener extends JedisPubSub{

        private String clientId;
        private PSubHandler handler;

        public PPrintListener(String clientId,Jedis jedis){
            this.clientId = clientId;
            handler = new PSubHandler(jedis);
        }

        @Override
        public void onMessage(String channel, String message) {
            //此处我们可以取消订阅
            if(message.equalsIgnoreCase("quit")){
                this.unsubscribe(channel);
            }
            handler.handle(channel, message);//触发当前订阅者从自己的消息队列中移除消息
        }

        private void message(String channel,String message){
            String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            System.out.println("message receive:" + message + ",channel:" + channel + "..." + time);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            System.out.println("message receive:" + message + ",pattern channel:" + channel);

        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            handler.subscribe(channel);
            System.out.println("subscribe:" + channel + ";total channels : " + subscribedChannels);

        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            handler.unsubscribe(channel);
            System.out.println("unsubscribe:" + channel + ";total channels : " + subscribedChannels);

        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {
            System.out.println("unsubscribe pattern:" + pattern + ";total channels : " + subscribedChannels);

        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            System.out.println("subscribe pattern:" + pattern + ";total channels : " + subscribedChannels);
        }

        @Override
        public void unsubscribe(String... channels) {
            super.unsubscribe(channels);
            for(String channel : channels){
                handler.unsubscribe(channel);
            }
        }

        class PSubHandler {

            private Jedis jedis;
            PSubHandler(Jedis jedis){
                this.jedis = jedis;
            }
            public void handle(String channel,String message){
                int index = message.indexOf("/");
                if(index < 0){
                    return;
                }
                Long txid = Long.valueOf(message.substring(0,index));
                String key = clientId + "/" + channel;
                while(true){
                    String lm = jedis.lindex(key, 0);//获取第一个消息
                    if(lm == null){
                        break;
                    }
                    int li = lm.indexOf("/");
                    //如果消息不合法，删除并处理
                    if(li < 0){
                        String result = jedis.lpop(key);//删除当前message
                        //为空
                        if(result == null){
                            break;
                        }
                        message(channel, lm);
                        continue;
                    }
                    Long lxid = Long.valueOf(lm.substring(0,li));//获取消息的txid
                    //直接消费txid之前的残留消息
                    if(txid >= lxid){
                        jedis.lpop(key);//删除当前message
                        message(channel, lm);
                        continue;
                    }else{
                        break;
                    }
                }
            }

            public void subscribe(String channel){
                String key = clientId + "/" + channel;
                boolean exist = jedis.sismember(SUBSCRIBE_CENTER,key);
                if(!exist){
                    jedis.sadd(SUBSCRIBE_CENTER, key);
                }
            }

            public void unsubscribe(String channel){
                String key = clientId + "/" + channel;
                jedis.srem(SUBSCRIBE_CENTER, key);//从“活跃订阅者”集合中删除
                jedis.del(key);//删除“订阅者消息队列”
            }
        }
    }

    //发布端
    private static String MESSAGE_TXID = "MESSAGE_ID";
    class PPubClient {

        private Jedis jedis;//
        public PPubClient(String host,int port){
            jedis = new Jedis(host,port);
        }

        /**
         * 发布的每条消息，都需要在“订阅者消息队列”中持久
         * @param message
         */
        private void put(String message){
            //期望这个集合不要太大
            Set<String> subClients = jedis.smembers(SUBSCRIBE_CENTER);
            for(String clientKey : subClients){
                jedis.rpush(clientKey, message);
            }
        }

        public void pub(String channel,String message){
            //每个消息，都有具有一个全局唯一的id
            //txid为了防止订阅端在数据处理时“乱序”，这就要求订阅者需要解析message
            Long txid = jedis.incr(MESSAGE_TXID);
            String content = txid + "/" + message;
            //非事务
            this.put(content);
            jedis.publish(channel, content);//为每个消息设定id，最终消息格式1000/messageContent

        }

        public void close(String channel){
            jedis.publish(channel, "quit");
            jedis.del(channel);//删除
        }

        public void test(){
            jedis.set("pub-block", "15");
            String tmp = jedis.get("pub-block");
            System.out.println("TEST:" + tmp);
        }
    }

    class PSubClient {

        private Jedis jedis;//
        private JedisPubSub listener;//单listener

        public PSubClient(String host,int port,String clientId){
            jedis = new Jedis(host,port);
            listener = new PPrintListener(clientId, new Jedis(host, port));
        }

        public void sub(String channel){
            jedis.subscribe(listener, channel);
        }

        public void unsubscribe(String channel){
            listener.unsubscribe(channel);
        }
    }

    @Test
    public void testPersistentMQ() throws InterruptedException {

        PPubClient pubClient = new PPubClient("192.168.1.118",6379);
        final String channel = "pubsub-channel-p";
        final PSubClient subClient = new PSubClient("192.168.1.118",6379,"subClient-1");
        Thread subThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("----------subscribe operation begin-------");
                //在API级别，此处为轮询操作，直到unsubscribe调用，才会返回
                subClient.sub(channel);
                System.out.println("----------subscribe operation end-------");

            }
        });
        subThread.setDaemon(true);
        subThread.start();
        int i = 0;
        while(i < 2){
            String message = RandomStringUtils.random(6, true, true);//apache-commons
            pubClient.pub(channel, message);
            i++;
            Thread.sleep(1000);
        }
        subClient.unsubscribe(channel);
    }


}
