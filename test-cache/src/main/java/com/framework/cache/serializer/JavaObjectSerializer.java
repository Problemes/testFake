package com.framework.cache.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by qinshucai on 2016/4/7.
 */
public class JavaObjectSerializer implements Serializer{
    /**
     * 是否以二进制存储
     * @return
     */
    public boolean needSaveAsBinary()
    {
        return true;
    }
    /**
     * 对象序列化
     * @param data
     * @return
     */
    public Object serialize(Object data) {
        ByteArrayOutputStream output= null;
        ObjectOutputStream out = null;
        try
        {
            output = new ByteArrayOutputStream();
            out = new ObjectOutputStream(output);
            out.writeObject(data);
            return output.toByteArray();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (output != null)
                    output.close();
                if(out != null)
                    out.close();
            }catch (Exception e){}
        }
    }

    /**
     * 反序列化
     * @param data
     * @return
     */
    public <T> T deserialize(Object data,Class<T> clazz) {
        ByteArrayInputStream input = null;
        ObjectInputStream in = null;
        try
        {
            byte[] bytes = (byte[])data;
            input = new ByteArrayInputStream(bytes);
            in = new ObjectInputStream(input);
            return (T)in.readObject();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (input != null)
                    input.close();
                if(in != null)
                    in.close();
            }catch (Exception e){}
        }

    }
}
