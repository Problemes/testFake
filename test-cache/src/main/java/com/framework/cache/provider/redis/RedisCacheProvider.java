package com.framework.cache.provider.redis;



import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by qinshucai on 2016/4/6.
 */
public class RedisCacheProvider{
    private static String JEDIS_CLIENT_CONFIG_FILE = "cache.redis.client.cfg.properties";
    private static JedisPool jedisPool = null;
    private static Logger  logger = Logger.getLogger(RedisCacheProvider.class);
    private static RedisCacheProvider instance;
    private static String instanceLock = "redisCacheProviderLock";

    private RedisCacheProvider()
            throws Exception
    {
        JedisPoolConfig config = new JedisPoolConfig();

        Properties properties = this.getProperties(JEDIS_CLIENT_CONFIG_FILE);
        String host = "localhost";
        int port = 6379;
        int timeout = 10000;

        if(properties.getProperty("redis.host") != null && !"".equals(properties.getProperty("redis.host").trim()))
            host = properties.getProperty("redis.host");
        if(properties.getProperty("redis.port") != null && !"".equals(properties.getProperty("redis.port").trim()))
            port = Integer.parseInt(properties.getProperty("redis.port"));
        String password = properties.getProperty("redis.password");
        /** redis访问超时时间 */
        if(properties.getProperty("redis.timeout") != null)
            timeout = Integer.parseInt(properties.getProperty("redis.timeout"));

        //将properties中的配置拷贝到bean中
        BeanUtils.populate(config, properties);
        //创建redis pool
        if(password != null && !"".equals(password.trim()))
        {
            jedisPool = new JedisPool(config,host,port,timeout,password);
        }
        else
        {
            jedisPool = new JedisPool(config,host,port,timeout);
        }
    }

    public static RedisCacheProvider getInstance()
            throws Exception
    {
        if(instance == null)
        {
            synchronized (instanceLock)
            {
                if(instance == null)
                    instance = new RedisCacheProvider();
            }
        }
        return instance;
    }


    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        return jedisPool.getResource();
    }


    /**
     * * Return jedis connection to the pool, call different return methods
     * depends on the conectionBroken status.
     */
    public static void closeResource(Jedis jedis, boolean conectionBroken) {
        try {
            if(jedis != null)
                jedis.close();
//            if (conectionBroken) {
//                jedisPool.getResource().cl;
//            } else {
//                jedisPool.returnResource(jedis);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * Handle jedisException, write log and return whether the connection is
     * broken.
     */
    public static boolean isBrokenException(JedisException jedisException) {
        if (jedisException instanceof JedisConnectionException) {
        } else if (jedisException instanceof JedisDataException) {
            if ((jedisException.getMessage() != null)
                    && (jedisException.getMessage().indexOf("READONLY") != -1)) {
            } else {
                return false;
            }
        } else {
        }
        return true;
    }

    /**
     * 获取配置文件
     * @return
     * @throws Exception
     */
    public  Properties getProperties(String propertyPath)
            throws Exception
    {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyPath);
            if (inputStream == null)
                inputStream = this.getClass().getResourceAsStream(propertyPath);


            if (inputStream == null)
                throw new Exception("config file:" + propertyPath + " not found!");
            else
                properties.load(inputStream);
        }finally {
            if(inputStream != null)
                inputStream.close();
        }
        return properties;
    }
}
