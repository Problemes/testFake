import com.framework.cache.CacheManager;
import com.framework.cache.CacheService;
import com.framework.cache.serializer.JSONSerializer;

import java.util.Date;

/**
 * Created by qinshucai on 2016/4/7.
 */
public class CacheServiceTest {
    CacheManager cacheManager = CacheManager.getInstance();
    CacheService cacheService = cacheManager.getCacheService(CacheManager.CACHE_PROVIDER_REDIS,new JSONSerializer());
    public void test()
    {
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

        cacheService.setObject("test", test);

        User ret = cacheService.getObject("test", User.class);

        System.out.println(ret);
    }

    public static void main(String args[])
    {
        try
        {
            CacheServiceTest client = new CacheServiceTest();
            client.test();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
