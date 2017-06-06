package com.framework.cache.serializer;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.PropertyNameProcessor;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by qinshucai on 2016/4/7.
 */
public class JSONSerializer implements Serializer {
    /**
     * 是否以二进制存储
     * @return
     */
    public boolean needSaveAsBinary()
    {
        return false;
    }

    @Override
    public Object serialize(Object data) {
        //todo 处理Java 数组子对象的转化
        return net.sf.json.JSONSerializer.toJSON(data);
    }

    @Override
    public <T> T deserialize(Object data,Class<T> clazz) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        JsonConfig config = new JsonConfig();
        config.setRootClass(clazz);
        return (T) net.sf.json.JSONSerializer.toJava(jsonObject, config);
    }
}
