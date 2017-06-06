package com.framework.cache.serializer;



/**
 * Created by qinshucai on 2016/4/7.
 */
public interface Serializer {
    /**
     * 对象序列化
     * @param data
     * @return
     */
    Object serialize(Object data);

    /**
     * 是否以二进制存储
     * @return
     */
    boolean needSaveAsBinary();

    /**
     * 反序列化
     * @param data
     * @return
     */
    public <T> T deserialize(Object data,Class<T> clazz);
}
