import com.framework.cache.CacheManager;
import com.framework.cache.CacheService;
import com.framework.cache.serializer.JavaObjectSerializer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by qinshucai on 2015/11/27.
 */
public class CacheTestThread extends Thread {
    Logger logger = Logger.getLogger(CacheTestThread.class);

    long cnt = 1;
    int reqDuration = -1;//两次请求之间的间隔ms
    List<String> readyThread;
    List<String> finishedThread;

    CacheService cacheService;

    CacheTestThread(CacheService cacheService, long count, int reqDuration, List<String> readyThread, List<String> finishedThread)
            throws Exception
    {
        this.cnt = count;
        this.reqDuration = reqDuration;
        this.readyThread = readyThread;
        this.finishedThread = finishedThread;

        this.cacheService = cacheService;
    }

    public void run()
    {
        try {
            //等待指令
            synchronized (this.readyThread)
            {
                readyThread.add("1");
                try {
                    //System.out.println("Client "+stid +" is waiting for command...");
                    this.readyThread.wait();
                    //System.out.println("Client "+stid +" is waiting for command...");
                }catch(Exception e){e.printStackTrace();}
            }

            //send message
            Long batch = System.currentTimeMillis();
            for(int i=0;i<cnt;i++)
            {

                try{
                    long start = System.currentTimeMillis();

                    User test = new User();
                    test.setUserName("测试");
                    test.setPassword("123456");
                    test.setHeight(100);
                    test.setTime(new Date());
                    Friend friend1 = new Friend();
                    friend1.setFriendName("张三");
                    friend1.setBirthDay(new Date());
                    test.addFriend(friend1);

                    Friend friend2 = new Friend();
                    friend2.setFriendName("王二");
                    friend2.setBirthDay(new Date());
                    test.addFriend(friend2);

                    //cacheService.setObject(batch+"-"+this.getId()+"-"+i, test);
                    cacheService.zcount("11","1","100");
                    //User ret = cacheService.getObject("test", User.class);

                    //System.out.println("Run time is:"+(System.currentTimeMillis()-start));
                    if (reqDuration > 0)
                        Thread.sleep(reqDuration);
                }catch (Exception e){e.printStackTrace();}
            }

            finishedThread.add("1");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 调用 Hello 服务
     * @param args
     */
    public static void main(String[] args) {
        try {
            long requests = 1000;
            int threads = 600;
            int reqDuration = 0;//两次请求之间的间隔.毫秒

            List<String> readyThread = new ArrayList<String>(threads);
            List<String> finishedThread = Collections.synchronizedList(new ArrayList<String>(threads));


            CacheManager cacheManager = CacheManager.getInstance();
            CacheService cacheService = cacheManager.getCacheService(CacheManager.CACHE_PROVIDER_REDIS,new JavaObjectSerializer());
            for(int i=0;i<threads;i++)
            {
                new CacheTestThread(cacheService,requests,reqDuration,readyThread,finishedThread).start();
            }

            //等待线程初始化完成
            while (readyThread.size() != threads)
            {
                System.out.println("Thread initialed:"+readyThread.size());
                Thread.sleep(500);
            }

            //开始计算运行时间
            long startSend = System.currentTimeMillis();
            synchronized (readyThread)
            {
                readyThread.notifyAll();
            }

            System.out.println("Thread initialed:"+readyThread.size());

            //等待处理结果完成
            while (finishedThread.size() != threads)
            {
                Thread.sleep(10);
            }

            //计算整体性能 = 总运行时间-sleep时间(按单线程算)
            long timeToSleep = reqDuration*requests;
            long totalTime = System.currentTimeMillis() - startSend;
            long actTime = totalTime-timeToSleep;

            //计算整体TPS (QPS（TPS）每秒钟request数量 = 并发数/平均响应时间==并发数*运行次数/总时间)
            long tps =   (long)(1.0*requests * 1000/actTime*threads);

            //计算单个请求平均响应时间=actTime/requests
            double avgLatency = ((long)(actTime*1.0/requests*100))/100.0;

            System.out.println("Total run time is:" + ((long)(totalTime*100.0/1000.0))/100.0 + "s (run:"+((long)(actTime*100.0/1000))/100.0+"s,sleep:"+((long)(timeToSleep*100.0/1000.0))/100.0+"s)on concurrent:"+threads+"  " +
                    "with TPS :" + tps + "reqs./s  and Latency:" + avgLatency + "ms/req. for " + requests*threads + "reqs.");


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
