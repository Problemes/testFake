package com.yh.api.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/1/12.
 */
public class testUtil {

    private static final String app_key = "e83ddc3e63883ef2";

    public static void main(String[] args) throws Exception {
        String body = "{\"usercode\":\"11606103\",\"content\":\"test GO\"}";
        String timestamp = String.format("%f", System.currentTimeMillis() / 1000.0);

        LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
        data.put("app_key", app_key);
        data.put("timestamp", timestamp);
        data.put("body", body);

        String sign = XiaoDouSign.getSign(data);
        data.put("sign", sign);
        String url = "http://api.jiaxiaobao.im/open/v1/message/sendmsg";
        String result = HttpClientPost(url, data, "utf-8");

        System.out.println(result);
    }

    /**
     * 执行一个 HTTP POST 请求，返回请求响应的数据
     *
     * @param url
     *            请求的URL地址
     * @param map
     *            请求参数Map
     * @param encode
     *            编码格式
     * @return
     */
    public static String HttpClientPost(String url, LinkedHashMap<String, String> map, String encode) {
        String result = "";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        try {
            // 实现将请求中的参数封装到请求参数中，请求体中
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, encode);
            // 使用post方式提交
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            // 指定post方式提交数据
            @SuppressWarnings({ "resource" })
            HttpClient client = new DefaultHttpClient();

            HttpResponse httpResponse = client.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = changeInputStream(httpResponse.getEntity().getContent(), encode);
            }
        } catch (Exception e) {
            System.out.println("post请求提交失败:" + url);
        }
        return result;
    }

    /**
     * 转换数据流
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputStream(InputStream inputStream, String encode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] date = new byte[1024];
        String result = "";
        try {
            while ((len = inputStream.read(date)) != -1) {
                outputStream.write(date, 0, len);
            }
            result = new String(outputStream.toByteArray(), encode);
        } catch (IOException e) {
            System.out.println("请求数据解析失败:");
        }
        return result;
    }


}
